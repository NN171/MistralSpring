package com.example.mistral.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class PdfRagService {

    private final Logger logger = LoggerFactory.getLogger(PdfRagService.class);

    @Autowired
    private VectorStore vectorStore;

    @Value("classpath:/Ozon.pdf")
    private Resource pdfFile;

    public void load() {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                pdfFile,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build())
                        .withPagesPerDocument(1).build());

        TokenTextSplitter splitter = new TokenTextSplitter();
        vectorStore.accept(splitter.apply(pdfReader.get()));
        logger.info("Data has loaded");
    }
}
