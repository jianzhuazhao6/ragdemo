package com.zjh.research.ragdemo.controller;

import com.zjh.research.ragdemo.dto.ChatDto;
import com.zjh.research.ragdemo.service.RagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rag")
@RequiredArgsConstructor
@Slf4j
public class RagController {

    private final RagService ragService;

    @PostMapping(value = "/ingest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> ingest(@RequestPart("file") Mono<FilePart> filePartMono) {
        return filePartMono.flatMap(filePart ->
                DataBufferUtils.join(filePart.content())
                        .map(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);

                            Resource resource = new ByteArrayResource(bytes) {
                                @Override
                                public String getFilename() {
                                    return filePart.filename();
                                }
                            };

                            ragService.ingestPdf(resource);
                            return "File " + filePart.filename() + " ingested successfully";
                        })
        );
    }

    @PostMapping("/query")
    public Mono<String> query(@RequestBody Mono<ChatDto> chatDtoMono) {
        return ragService.query(chatDtoMono);
    }
}
