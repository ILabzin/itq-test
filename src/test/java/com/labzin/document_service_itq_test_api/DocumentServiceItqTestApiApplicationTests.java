package com.labzin.document_service_itq_test_api;

import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;
import com.labzin.document_service_itq_test_api.persistance.repository.DocumentRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
@SpringBootTest
class DocumentServiceItqTestApiApplicationTests {
}
