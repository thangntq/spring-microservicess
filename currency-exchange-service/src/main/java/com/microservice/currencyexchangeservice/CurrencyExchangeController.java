package com.microservice.currencyexchangeservice;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyRepository currencyRepository;
    private final Environment environment;
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable("from")String from,
            @PathVariable("to")String to){
        CurrencyExchange currencyExchange = currencyRepository.findByFromAndTo(from,to);
        if (currencyExchange == null){
            throw new RuntimeException("Unable to find data for " + from +" to "+ to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);

        return currencyExchange;

    }
}
