package br.ufrn.imd.domain;

public class CurrencyQuotation {

    private String currency;
    private String rate;

    public CurrencyQuotation(String currency, String rate) {
        this.setCurrency(currency);
        this.setRate(rate);
    }

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		if (currency.equalsIgnoreCase("EUR to BRL")) {
			this.currency = "Euro";
		} 
		else if (currency.equalsIgnoreCase("USD to BRL")) {
			this.currency = "Dólar Americano";
		}
		else if (currency.equalsIgnoreCase("CAD to BRL")) {
			this.currency = "Dólar Canadense";
		}
		else if (currency.equalsIgnoreCase("AUD to BRL")) {
			this.currency = "Dólar Australiano";
		}
		else if (currency.equalsIgnoreCase("GBP to BRL")) {
			this.currency = "Libra Esterlina";
		}
		else if (currency.equalsIgnoreCase("CHF to BRL")) {
			this.currency = "Franco Suiço";
		}
		else if (currency.equalsIgnoreCase("JPY to BRL")) {
			this.currency = "Yene";
		}
		else if (currency.equalsIgnoreCase("CNY to BRL")) {
			this.currency = "Yuan";
		}
		else if (currency.equalsIgnoreCase("INR to BRL")) {
			this.currency = "Rúpia Indiana";
		}
		else if (currency.equalsIgnoreCase("RUB to BRL")) {
			this.currency = "Rublo";
		}
		else {
			this.currency = currency;			
		}
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
