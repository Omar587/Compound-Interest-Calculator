package InterestCalculator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class User {


    private final double principle;
    private final int annualInterest;
    private final String compoundingFrequency;
    private final int years;



    public User(double principle, int annualInterest, String compoundingFrequency, int years) {
        this.principle = principle;
        this.annualInterest = annualInterest;
        this.compoundingFrequency = compoundingFrequency;
        this.years = years;
    }



    public double getPrinciple() {
        return principle;
    }

    public int getAnnualInterest() {
        return annualInterest;
    }

    public String getCompoundingFrequency() {
        return compoundingFrequency;
    }

    public int getYears() {
        return years;
    }


    /**Helper function to determine the closing balance from the initial investment
     * Interest formula  I= P(1 +r/n)^nt -P**/

    public double compoundInterestGained(int years){
        Map<String, Integer> frequency = new HashMap<>();
        frequency.put("Monthly",12 ); frequency.put("Semi-Annually",2);
        frequency.put("Quarterly",4); frequency.put("Annually", 1);

        double  n = frequency.get(getCompoundingFrequency());
        double p = getPrinciple();
        double t = years;
        double r = getAnnualInterest()/100.00;

        double compoundIntrest =  (p * Math.pow(1 + (r/n), n*t)) - p;
        return compoundIntrest;

    }

    /**
     * helper method used to deisplay the interest column
     *
     * @return string represeting the interet gaint
     */

    public String getInterest() {

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String interest = nf.format(compoundInterestGained(getYears()) - compoundInterestGained(getYears() - 1));

        return interest;

    }

    /**
     * helper method used to display the closing balance columns of
     * the table view, also the y axis of bar chart
     *
     *
     * @return string closing balance
     */

    public String getClosingBalance() {

        NumberFormat nf = NumberFormat.getCurrencyInstance();

        double num =getPrinciple() + compoundInterestGained(getYears());
        String closingBalance = nf.format(num);

        return closingBalance;

    }

    /**
     * helper method used to display th the opening balance column for the table view
     *
     * @return string opening balance
     */
    public String getOpeningBalance() {

        NumberFormat nf = NumberFormat.getCurrencyInstance();

        if ( getYears() == 1) {

            return nf.format(principle);


        }else{

            double num = principle + compoundInterestGained(getYears() -1);
            return nf.format(num );

        }


    }




    @Override
    public String toString() {
        return "Person{" +
                "principle=" + principle +
                ", annualInterest=" + annualInterest +
                ", compoundInterestGained=" + compoundInterestGained(getYears()) +
                ", compoundingFrequency='" + compoundingFrequency + '\'' +
                ", years=" + years +
                '}';
    }




}
