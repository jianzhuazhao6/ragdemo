package com.zjh.research.ragdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
@Slf4j
@RequiredArgsConstructor
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient.Builder chatClientBuilder;

    public void ingestPdf(Resource pdfResource) {
        log.info("Ingesting PDF: {}", pdfResource.getFilename());
        
        // 1. Load the PDF document
        TikaDocumentReader reader = new TikaDocumentReader(pdfResource);
        List<Document> documents = reader.get();
        log.info("Loaded {} documents from PDF", documents.size());

        // 2. Split into text chunks
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(documents);
        log.info("Split into {} chunks", chunks.size());

        // 3. Store in Vector Database (MongoDB)
        // This will automatically use Ollama for embeddings as configured
        vectorStore.accept(chunks);
        log.info("Successfully stored chunks in MongoDB Vector Store");
    }

    public String query(String question) {
        ChatClient chatClient = chatClientBuilder
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();

        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
