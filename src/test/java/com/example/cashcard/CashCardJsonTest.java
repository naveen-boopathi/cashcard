package com.example.cashcard;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45, "vrish"),
                new CashCard(100L, 100.00, "vrish"),
                new CashCard(101L, 150.00, "vrish")
        );
    }

    @Test
    public void myFirstTest() {
        assertThat(42).isEqualTo(42);
    }

    @Test
    public void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    public void cashCardListDeserializationTest() throws IOException {
        String expected = """
                [
                    { "id": 99, "amount": 123.45, "owner": "vrish" },
                    { "id": 100, "amount": 100.00, "owner": "vrish" },
                    { "id": 101, "amount": 150.00, "owner": "vrish" }
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
    }

    @Test
    public void cashCardSerializationTest() throws IOException {
        CashCard cashCard = cashCards[0];
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(cashCard)).hasJsonPathValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }

    @Test
    public void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id":99,
                    "amount":123.45,
                    "owner": "vrish"
                }
                """;
        assertThat(json.parse(expected)).isEqualTo(new CashCard(99L, 123.45, "vrish"));
        assertThat(json.parseObject(expected).id()).isEqualTo(99L);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }
}