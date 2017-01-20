package loan;

import java.text.NumberFormat;

/**
 * Created by ronnykibet on 12/29/16.
 */
public class ScheduleItem {


    private double monthlyPayment;
    private int month;
    private double interestPaid;
    private double principalPaid;
    private double newBalance;
    private double cumulativeInterest;

    private String monthlyPaymentFormatted;
    private String interestPaidFormatted;
    private String principalPaidFormatted;
    private String newBalanceFormatted;
    private String cumulativeInterestFormatted;


    public ScheduleItem(){}

    public ScheduleItem(double monthlyPayment, int month, double interestPaid, double principalPaid, double newBalance, double cumulativeInterest) {
        this.monthlyPayment = monthlyPayment;
        this.month = month;
        this.interestPaid = interestPaid;
        this.principalPaid = principalPaid;
        this.newBalance = newBalance;
        this.cumulativeInterest = cumulativeInterest;
    }

    public double getMonthlyPayment() {

        return monthlyPayment;
    }

    public String getMonthlyPaymentFormatted() {
        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance();
        double monthlypaymentRounded = (double) Math.round(monthlyPayment * 100) / 100;

        return currencyFormat.format(monthlypaymentRounded);
    }

    public int getMonth() {
        return month;
    }

    public double getInterestPaid() {

        return interestPaid;
    }

    public String getInterestPaidFormatted() {
        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance();
        double interestPaidRounded = (double) Math.round(interestPaid * 100) / 100;
        return currencyFormat.format(interestPaidRounded);
    }

    public double getPrincipalPaid() {

        return principalPaid;
    }

    public String getPrincipalPaidFormatted() {
        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance();
        double principalROunded = (double) Math.round(principalPaid * 100) / 100;
        return currencyFormat.format(principalROunded);
    }

    public double getNewBalance() {
        return newBalance;
    }

    public String getNewBalanceFormatted() {
        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance();
        double newBalanceRounded = (double) Math.round(newBalance * 100) / 100;
        return currencyFormat.format(newBalanceRounded);
    }

    public double getCumulativeInterest() {
        return cumulativeInterest;
    }

    public String getCumulativeInterestFormatted() {
        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance();
        double cumulativeInterestRounded = (double) Math.round(cumulativeInterest * 100) / 100;
        return currencyFormat.format(cumulativeInterestRounded);
    }
}
