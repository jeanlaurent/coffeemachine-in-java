package coffeemachine;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static coffeemachine.Drink.*;

public class CoffeMachineProtocol {

    public static final int MAX_NUMBER_OF_SUGAR = 2;
    private final Map<Drink, Integer> map;
    private final EmailNotifier emailNotifier;
    private final BeverageChecker checker;
    private int money;

    public CoffeMachineProtocol(EmailNotifier emailNotifier, BeverageChecker  checker) {
        this.emailNotifier = emailNotifier;
        this.checker = checker;
        map = new HashMap<>();
        money = 0;
    }

    public String translate(Drink drink, int numberOfSugar, int money, boolean isExtraHotRequested) {
        int priceDifference = drink.getPrice() - money;
        if (priceDifference > 0) {
            return "M: Il manque " + priceDifference + " C.";
        }
        if (map.containsKey(drink)) {
            map.put(drink, map.get(drink) + 1);
        } else {
            map.put(drink, 1);
        }
        this.money += drink.getPrice();
        String extraHot = handleExtraHot(isExtraHotRequested);
        String sugar = handleSugar(drink, numberOfSugar);
        String touillette = handleTouillette(sugar);
        return drink.getCode() + extraHot + ":" + sugar + ":" + touillette;
    }

    public String print() {
        List<String> strings = new ArrayList<>();
        for (Drink drink : Drink.values()) {
            strings.add(drink.name().toLowerCase() + ":" +map.get(drink));
        }
        strings.add("total:" + money);
        return String.join(",", strings);
    }

    private String handleTouillette(String sugar) {
        String touillette = "";
        if (sugar.length() > 0) {
            touillette = "0";
        }
        return touillette;
    }

    private String handleSugar(Drink drink, int numberOfSugar) {
        String sugar = "";
        if (!drink.equals(ORANGE)) {
            if (numberOfSugar > MAX_NUMBER_OF_SUGAR) {
                numberOfSugar = MAX_NUMBER_OF_SUGAR;
            }
            if (numberOfSugar > 0) {

                sugar = String.valueOf(numberOfSugar);
            }
        }
        return sugar;
    }

    private String handleExtraHot(boolean isExtraHotRequested) {
        String extraHot = "";
        if (isExtraHotRequested) {
            extraHot = "h";
        }
        return extraHot;
    }

}
