import java.util.Random;

/**
 * Class that represents a single test factor.
 */
public class Compound {
    public String name;
    public double high;
    public double low;
    public double value;
    public int condition;

    /**
     * Constructor that is supposed to set all needed information about the compound. Exact values are
     * randomly generated according to a normal distribution based on the minimum and maximum provided as parameters.
     * When there is an exception, such as: there is no maximum value, the minimum value equals 0 or both minimum
     * and maximum values are equal to 0, then the distribution is set to provide appearance of anomalies with
     * the same frequency as in ordinary case. For the purposes of this project the standard deviation has been
     * increased to generate more anomalies and therefore present full functionality of the program.
     * The class field condition represents whether the value is too low, in norm or too high (sequentially -1, 0, 1).
     *
     * @param name the name of the compound
     * @param low minimum standard value
     * @param high maximum standard value
     */
    public Compound(String name, String low, String high) {
        this.name = name;
        Random rand = new Random();

        try {
            if (low.equals("0.0") & high.equals("0.0")) {
                this.low = 0.0;
                this.high = 0.0;
                double mean = -1;

                //double deviation = 1;
                double deviation = 0.5;

                value = rand.nextGaussian() * deviation + mean;
                value = Math.round(value * 100.0) / 100.0;
                if (value < 0)
                    value = 0;
                condition = (value > 0) ? 1 : 0;
            } else if (high.equals("-")) {
                this.low = Double.parseDouble(low);
                this.high = Double.POSITIVE_INFINITY;
                double virHigh = this.low * 1.6;
                double mean = (this.low + virHigh) / 2;

                //double deviation = (virHigh - this.low) / 3;
                double deviation = (virHigh - this.low);

                value = rand.nextGaussian() * deviation + mean;
                if (value > virHigh)
                    value = value - this.low;
                value = Math.abs(value);
                value = Math.round(value * 100.0) / 100.0;
                condition = (value < this.low) ? -1 : 0;
            } else if (low.equals("0.0")) {
                this.low = Double.parseDouble(low);
                this.high = Double.parseDouble(high);
                double mean = (this.high + this.low) / 2;

                //double deviation = (this.high - this.low) / 3;
                double deviation = (this.high - this.low);

                value = rand.nextGaussian() * deviation + mean;
                if (value < 0)
                    value = Math.abs(value - this.high);
                value = Math.round(value * 100.0) / 100.0;
                condition = (value > this.high) ? 1 : 0;
            } else {
                this.low = Double.parseDouble(low);
                this.high = Double.parseDouble(high);
                double mean = (this.high + this.low) / 2;

                //double deviation = (this.high - this.low) / 3;
                double deviation = (this.high - this.low);

                value = rand.nextGaussian() * deviation + mean;
                if (value < 0)
                    value = rand.nextDouble(0, this.low);
                value = Math.round(value * 100.0) / 100.0;
                if (value > this.high)
                    condition = 1;
                else if (value < this.low)
                    condition = -1;
                else
                    condition = 0;
            }
        } catch (Exception ex) {
            System.out.println("Incorrect position in standard database!");
        }
    }

    /**
     * Method that passes the information about the object into String
     *
     * @return String with the compound data
     */
    @Override
    public String toString() {
        return name + "/" + condition + ":" + value;
    }
}
