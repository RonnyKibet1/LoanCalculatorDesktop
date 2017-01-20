package loan;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller  implements Initializable {

    @FXML
    private TextField loanAmountTextField;
    @FXML private TextField loanTermYearsTextField;
    @FXML private TextField loanTermMonthsTextField;
    @FXML private TextField loanPercentageTextField;
    @FXML
    private Button calculateButton;

    //labels
    @FXML private Pane resultsPanel;
    @FXML private Label monthlyPaymentLabel;
    @FXML private Label numberOFPaymentsLabel;
    @FXML private Label loanAmountLabel;
    @FXML private Label loanInterestRateLabel;
    @FXML private Label loanTermLabel;
    @FXML private Label totalInterestLabel;
    @FXML private Label totalPaymentsLabel;

    //anchor panes
    @FXML private AnchorPane listPane;
    @FXML private Button scheduleButton;
    @FXML private AnchorPane mainPane;
    @FXML private Button closeButton;


    private double loanAmount = 0.0;
    private int termInyears = 0;
    private double interestRate = 0.0;
    private int termInMonths = 0;

    //table
    @FXML private TableView<ScheduleItem> table;
    @FXML private TableColumn<ScheduleItem, Integer> monthColumn;
    @FXML private TableColumn<ScheduleItem, String> monthPaymountColumn;
    @FXML private TableColumn<ScheduleItem, Double> principalPaymentColumn;
    @FXML private TableColumn<ScheduleItem, Double> interestColumn;
    @FXML private TableColumn<ScheduleItem, Double> totalInterestColumn;
    @FXML private TableColumn<ScheduleItem, Double> balanceColumn;


    private ArrayList<ScheduleItem> scheduleItemList = new ArrayList<ScheduleItem>();

    private void populateScheduleTableView(){

        monthColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, Integer>("month"));
        monthPaymountColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("monthlyPaymentFormatted"));
        principalPaymentColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, Double>("principalPaidFormatted"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, Double>("interestPaidFormatted"));
        totalInterestColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, Double>("cumulativeInterestFormatted"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, Double>("newBalanceFormatted"));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        resultsPanel.setVisible(false);

        //hide the list pane
        listPane.setVisible(false);

        scheduleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(mainPane.isVisible()){
                    mainPane.setVisible(false);
                    listPane.setVisible(true);

                }
                calculateLoan();
                populateScheduleTableView();

                table.getItems().setAll(scheduleItemList);
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(listPane.isVisible()){
                    listPane.setVisible(false);
                    mainPane.setVisible(true);

                }
            }
        });

        //listen to calculate button click
        calculateButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                //calculate
                calculateLoan();

                resultsPanel.setVisible(true);


            }
        });

    }

    private void calculateLoan(){
        //get inputs
        String loanAmountInput = loanAmountTextField.getText().trim();
        String loanTermYearsInput = loanTermYearsTextField.getText().trim();
        String loanTermMonthInput = loanTermMonthsTextField.getText().trim();
        String loanPercentageInput = loanPercentageTextField.getText().trim();

        loanAmount = getDouble(loanAmountInput);
        termInyears = getInt(loanTermYearsInput);
        termInMonths = getInt(loanTermMonthInput);
        interestRate = getDouble(loanPercentageInput);


        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance();
        NumberFormat interestFormat =
                NumberFormat.getPercentInstance();

        System.out.println("Loan amount :" + currencyFormat.format(loanAmount));
        System.out.println("Terms in years :" + termInyears);
        System.out.println("Term in months :" + termInMonths);
        System.out.println("Interest rate :" + interestFormat.format(interestRate));

        printAmortizationSchedule(loanAmount, interestRate, termInyears, termInMonths);

        numberOFPaymentsLabel.setText("Number of payments: " + ((termInyears * 12) + termInMonths));
        loanAmountLabel.setText("Loan amount: " + currencyFormat.format(loanAmount));
        loanInterestRateLabel.setText("Interest rate :" + interestFormat.format(interestRate));
        if(termInyears > 1){
            loanTermLabel.setText("Loan term: " + termInyears + " years " + termInMonths + " months");
        }else{
            loanTermLabel.setText("Loan term: " + termInyears + " year " + termInMonths + " months");
        }
    }

    private int getInt(String test){
        try{
            return Integer.parseInt(test.trim());
        }catch(Exception e){
            return 0;
        }
    }

    private double getDouble(String test){
        try{
            return Double.parseDouble(test.trim());
        }catch(Exception e){
            return 0;
        }
    }

    private static double monthlyPayment(double loanAmount, double monthlyInterestRate, int numberOfYears, int numofMonths) {
        monthlyInterestRate /= 100;  // e.g. 5% => 0.05
        return loanAmount * monthlyInterestRate /
                ( 1 - 1 / Math.pow(1 + monthlyInterestRate, (numberOfYears * 12)+ numofMonths) );
    }

    public  void printAmortizationSchedule(double principal, double annualInterestRate,
                                                 int numYears, int numOFMonths) {
        double interestPaid, principalPaid, newBalance;
        double monthlyInterestRate, monthlyPayment;
        int month;
        int numMonths = (numYears * 12) + numOFMonths;
        double cumulativeInterest = 0;

        // Output monthly payment and total payment
        monthlyInterestRate = annualInterestRate / 12;
        monthlyPayment      = monthlyPayment(principal, monthlyInterestRate, numYears, numOFMonths);

        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance();
        System.out.format("Monthly Payment: %8.2f%n", monthlyPayment);
        System.out.format("Total Payment:   %8.2f%n", monthlyPayment * ((numYears * 12) + numOFMonths));
        //total payment
        double totalPayment = monthlyPayment * ((numYears * 12) + numOFMonths);
        double totalPaymentRounded = (double) Math.round(totalPayment * 100) / 100;
        totalPaymentsLabel.setText("Total Payment: "+ currencyFormat.format(totalPaymentRounded));
        //monthly payment rounded
        double monthlyPaymentRounded = (double) Math.round(monthlyPayment * 100) / 100;
        monthlyPaymentLabel.setText("Monthly payments: " + currencyFormat.format(monthlyPaymentRounded));

        //total interest
        double totalInterest = totalPaymentRounded - loanAmount;
        double totalInterestROunded = (double) Math.round(totalInterest * 100) / 100;
        String totalInterestCurrencyFormat = currencyFormat.format(totalInterestROunded);
        totalInterestLabel.setText("Total interest: " + totalInterestCurrencyFormat);


        scheduleItemList.clear();

        // Print the table header
        printTableHeader();

        for (month = 1; month <= numMonths; month++) {
            // Compute amount paid and new balance for each payment period
            interestPaid  = principal      * (monthlyInterestRate / 100);
            principalPaid = monthlyPayment - interestPaid;
            newBalance    = principal      - principalPaid;
            cumulativeInterest += interestPaid;

            //create new schedule item and add to list of schedules
            ScheduleItem item = new ScheduleItem(monthlyPayment, month, interestPaid, principalPaid, newBalance, cumulativeInterest);
            scheduleItemList.add(item);

            // Output the data item
            printScheduleItem(monthlyPayment, month, interestPaid, principalPaid, newBalance, cumulativeInterest);

            // Update the balance
            principal = newBalance;
        }
    }

    private  void printScheduleItem(double monthlyPayment, int month, double interestPaid,
                                          double principalPaid, double newBalance, double cumulativeInterest) {
        System.out.format("%8d%12.2f%10.2f%10.2f%12.2f%12.2f\n",
                month, monthlyPayment, principalPaid, interestPaid, newBalance, cumulativeInterest);
    }

    /**
     * Prints the table header for the amortization schedule.
     */
    private  void printTableHeader() {
        System.out.println("\nAmortization schedule");
        for(int i = 0; i < 40; i++) {  // Draw a line
            System.out.print("-");
        }
        System.out.format("\n%8s%12s%10s%10s%12s%12s\n",
                "Month#", "Payment", "Principal", "Interest", "Balance", " Total Interest");
        System.out.format("%8s%10s%10s%12s\n\n",
                "", "", "", "");
    }
}
