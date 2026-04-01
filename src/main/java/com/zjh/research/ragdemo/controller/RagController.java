package com.zjh.research.ragdemo.controller;

import com.zjh.research.ragdemo.service.RagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
@Slf4j
public class RagController {

    private final RagService ragService;

    @PostMapping(value = "/ingest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> ingest(@RequestPart("file") Mono<FilePart> filePartMono) {
        return filePartMono.flatMap(filePart -> Mono.fromCallable(() -> {
            Path tempFile = Files.createTempFile("upload-", "-" + filePart.filename());
            filePart.transferTo(tempFile).block();

            Resource resource = new FileSystemResource(tempFile.toFile());
            ragService.ingestPdf(resource);

            return "File " + filePart.filename() + " ingested successfully";
        }));
    }

    @GetMapping("/query")
    public String query(@RequestParam(value = "message") String message) {
        return ragService.query(message);
    }
}
