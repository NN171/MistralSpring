package com.example.mistral.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

@Configuration
public class RagConfig {

    @Value("classpath:/Busy.txt")
    private Resource familyBusy;

    @Value("classpath:/FamilyFavorities.txt")
    private Resource familyFavorities;

    @Bean
    @Qualifier("Busy")
    VectorStore busyVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore
                = new SimpleVectorStore(embeddingModel);

        File vectorStoreFile =
                new File("./busy_vector_store.json");

        if (vectorStoreFile.exists()) {
            System.out.println("Loaded Busy Vector Store File!");

            vectorStore.load(vectorStoreFile);
        } else {
            System.out.println("Create Busy Vector File");

            TextReader textReaderBusy = new TextReader(familyBusy);
            textReaderBusy.getCustomMetadata()
                    .put("filename", "Busy.txt");

            List<Document> documentsBusy = textReaderBusy.get();

            TextSplitter textSplitter = new TokenTextSplitter(
                    500,
                    200,
                    30,
                    5000,
                    false
            );
            List<Document> splitDocumentsBusy = textSplitter.apply(documentsBusy);

            vectorStore.add(splitDocumentsBusy);
            vectorStore.save(vectorStoreFile);
        }

        System.out.println("Busy Vector Store initialized successful");
        return vectorStore;
    }

    @Bean
    @Qualifier("Fav")
    VectorStore favVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore
                = new SimpleVectorStore(embeddingModel);

        File vectorStoreFile =
                new File("./fav_vector_store.json");

        if (vectorStoreFile.exists()) {
            System.out.println("Loaded Fav Vector Store File!");

            vectorStore.load(vectorStoreFile);
        } else {
            System.out.println("Create Fav Vector File");
            TextReader textReaderFavorite = new TextReader(familyFavorities);
            textReaderFavorite.getCustomMetadata()
                    .put("filename", "FamilyFavorities.txt");

            List<Document> documentsFavorite = textReaderFavorite.get();

            TextSplitter textSplitter = new TokenTextSplitter(
                    500,
                    200,
                    30,
                    5000,
                    false
            );
            List<Document> splitDocumentsFavorite = textSplitter.apply(documentsFavorite);

            vectorStore.add(splitDocumentsFavorite);
            vectorStore.save(vectorStoreFile);
        }

        System.out.println("Fav Vector Store initialized successful");
        return vectorStore;
    }
}
