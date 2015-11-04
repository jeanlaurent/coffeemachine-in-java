package coffeemachine;

import org.junit.Before;
import org.junit.Test;


import java.util.stream.IntStream;

import static coffeemachine.Drink.*;
import static java.util.stream.IntStream.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoffeeMachineTest {
    private CoffeMachineProtocol protocol;
    private EmailNotifier emailNotifier;
    private BeverageChecker checker;

    @Before
    public void init() {
        emailNotifier = mock(EmailNotifier.class);
        checker = mock(BeverageChecker.class);
        protocol = new CoffeMachineProtocol(
                emailNotifier,
                checker
        );
    }

    @Test
    public void should_return_chocolate_when_ordered() {
        assertThat(protocol.translate(CHOCOLATE, 0, 100, false)).isEqualTo("H::");
    }

    @Test
    public void should_return_tea_when_ordered() {
        assertThat(protocol.translate(TEA, 0, 100, false)).isEqualTo("T::");
    }

    @Test
    public void should_return_coffee_when_ordered() {
        assertThat(protocol.translate(COFFEE, 0, 100, false)).isEqualTo("C::");
    }

    @Test
    public void should_order_coffee_with_two_sugars() {
        assertThat(protocol.translate(COFFEE, 2, 100, false)).isEqualTo("C:2:0");
    }

    @Test
    public void should_order_tea_with_3_sugars() {
        assertThat(protocol.translate(TEA, 3, 100, false)).isEqualTo("T:2:0");
    }

    @Test
    public void should_make_drink_when_there_is_enough_money() {
        assertThat(protocol.translate(TEA, 3, 40, false)).isEqualTo("T:2:0");
    }

    @Test
    public void should_refuse_to_make_drink_when_there_is_not_enough_money() {
        assertThat(protocol.translate(TEA, 3, 30, false)).contains("10");
    }

    @Test
    public void should_order_an_orange_juice() {
        assertThat(protocol.translate(ORANGE, 3, 60, false)).isEqualTo("O::");
    }

    @Test
    public void should_order_a_coffee_extra_hot() {
        assertThat(protocol.translate(COFFEE, 0, 60, true)).isEqualTo("Ch::");
    }

    @Test
    public void should_write_drinks_in_report() {
        orderABunchOfDrink();
        assertThat(protocol.print()).startsWith("chocolate:1,tea:4,coffee:3,orange:2");
    }

    @Test
    public void should_write_money_in_report() {
        orderABunchOfDrink();
        assertThat(protocol.print()).endsWith("total:510");
    }


    @Test
    public void should_check_we_have_enough_coffee() {
        when(checker.isEmpty("Coffee")).thenReturn(true);


    }


    private void orderABunchOfDrink() {
        protocol.translate(CHOCOLATE, 0, 60, false);
        range(0, 2).forEach( i -> protocol.translate(ORANGE, 0, 60, false));
        range(0, 3).forEach( i -> protocol.translate(COFFEE, 0, 60, false));
        range(0, 4).forEach( i -> protocol.translate(TEA, 0, 60, false));
    }


}
