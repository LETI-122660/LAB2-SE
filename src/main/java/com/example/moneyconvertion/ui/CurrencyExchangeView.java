package com.example.moneyconvertion.ui;

import com.example.base.ui.component.ViewToolbar;
import com.example.moneyconvertion.CurrencyExchangeService;
import com.example.moneyconvertion.CurrencyExchange;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("currency-exchange")
@PageTitle("Currency Exchange")
@Menu(order = 2, icon = "vaadin:exchange", title = "Currency Exchange")
class CurrencyExchangeView extends Main {

    private final CurrencyExchangeService exchangeService;

    final ComboBox<String> fromCurrency;
    final ComboBox<String> toCurrency;
    final NumberField amountField;
    final Button exchangeBtn;
    final TextField resultField;
    final Grid<CurrencyExchange> exchangeGrid;

    CurrencyExchangeView(CurrencyExchangeService exchangeService) {
        this.exchangeService = exchangeService;

        fromCurrency = new ComboBox<>("From", "USD", "EUR", "GBP");
        toCurrency = new ComboBox<>("To", "USD", "EUR", "GBP");
        amountField = new NumberField("Amount");
        amountField.setMin(0);
        resultField = new TextField("Result");
        resultField.setReadOnly(true);

        exchangeBtn = new Button("Exchange", event -> exchange());
        exchangeBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        exchangeGrid = new Grid<>();
        exchangeGrid.setItems(exchangeService.listAll());
        exchangeGrid.addColumn(CurrencyExchange::getFromCurrency).setHeader("From");
        exchangeGrid.addColumn(CurrencyExchange::getToCurrency).setHeader("To");
        exchangeGrid.addColumn(CurrencyExchange::getAmount).setHeader("Amount");
        exchangeGrid.addColumn(CurrencyExchange::getResult).setHeader("Result");
        exchangeGrid.addColumn(e -> e.getExchangeDate().toString()).setHeader("Date");
        exchangeGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Currency Exchange", ViewToolbar.group(fromCurrency, toCurrency, amountField, exchangeBtn)));
        add(resultField, exchangeGrid);
    }

    private void exchange() {
        try {
            String from = fromCurrency.getValue();
            String to = toCurrency.getValue();
            Double amount = amountField.getValue();
            if (from == null || to == null || amount == null) {
                Notification.show("Please select currencies and enter an amount.", 3000, Notification.Position.BOTTOM_END)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            CurrencyExchange exchange = exchangeService.convertAndSave(from, to, amount);
            resultField.setValue(String.format("%.2f", exchange.getResult()));
            exchangeGrid.setItems(exchangeService.listAll());
            Notification.show("Exchange successful", 2000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage(), 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
