package tech.ada.bootcamp.arquitetura.cartaoservice.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Card;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.enums.PaymentMethod;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.request.PurchaseRequest;
import tech.ada.bootcamp.arquitetura.cartaoservice.payloads.response.ResponsePurchase;
import tech.ada.bootcamp.arquitetura.cartaoservice.repositories.PurchaseRepository;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository repository;

    @Mock
    private CardService cardService;

    @InjectMocks
    private PurchaseService service;

    private PurchaseRequest purchaseRequest;
    private PurchaseRequest purchasePixRequest;
    private UUID mockUUID; // Armazena o UUID aqui

    @BeforeEach
    void setup() {
        mockUUID = UUID.randomUUID(); // Gere o UUID uma vez
        purchaseRequest = new PurchaseRequest(mockUUID, "Generic Store", BigDecimal.ONE, PaymentMethod.CREDIT);
        purchasePixRequest = new PurchaseRequest(mockUUID, "Generic Store", BigDecimal.ONE, PaymentMethod.PIX);
    }

    @Test
    @DisplayName("Should successfully create a new purchase.")
    void shouldSuccessfullyCreateNewPurchase() {

        // Given
        Mockito.when(cardService.findOrFailById(mockUUID)).thenReturn(new Card());

        // Act - when
        ResponsePurchase responsePurchase = service.execute(purchaseRequest);

        // Assert / Verify
        Assertions.assertNotNull(responsePurchase);
        Mockito.verify(cardService, Mockito.times(1)).findOrFailById(mockUUID);
    }

    @Test
    @DisplayName("Should successfully create a new purchase with PIX.")
    void shouldSuccessfullyCreateNewPixPurchase() {

        // Given
        Mockito.when(cardService.findOrFailById(mockUUID)).thenReturn(new Card());

        // Act - when
        ResponsePurchase responsePurchase = service.execute(purchasePixRequest);

        // Assert / Verify
        Assertions.assertNotNull(responsePurchase);
        Mockito.verify(cardService, Mockito.times(1)).findOrFailById(mockUUID);
        Mockito.verify(purchaseRepository, Mockito.times(1)).save(Mockito.any());
    }
}