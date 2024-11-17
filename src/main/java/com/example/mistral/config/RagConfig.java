package com.example.mistral.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
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
    VectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore
                = new SimpleVectorStore(embeddingModel);

        File vectorStoreFile =
                new File("./vector_store.json");

        if(vectorStoreFile.exists()) {
            System.out.println("Loaded Vector Store File!");

            vectorStore.load(vectorStoreFile);
        } else {
            System.out.println("Create Vector File");

            TextReader textReaderBusy = new TextReader(familyBusy);
            TextReader textReaderFavorite = new TextReader(familyBusy);
            textReaderBusy.getCustomMetadata()
                    .put("filename", "Busy.txt");
            textReaderFavorite.getCustomMetadata()
                    .put("filename", "FamilyFavorities.txt");

            List<Document> documentsBusy = textReaderBusy.get();
            List<Document> documentsFavorite = textReaderFavorite.get();

            TextSplitter textSplitter = new TokenTextSplitter(
                    500,
                    200,
                    10,
                    5000,
                    false
            );
            List<Document> splitDocumentsBusy = textSplitter.apply(documentsBusy);
            List<Document> splitDocumentsFavorite = textSplitter.apply(documentsFavorite);

            vectorStore.add(splitDocumentsBusy);
            vectorStore.add(splitDocumentsFavorite);
            vectorStore.save(vectorStoreFile);
        }

        System.out.println("Vector Store initialized successful");
        return vectorStore;
    }
}
