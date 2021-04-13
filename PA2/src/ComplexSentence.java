import java.util.ArrayList;
import java.util.List;

/**
 * @author Chad Morrow
 * Object to hold the seperated sentences after parsing the text file
 */
public class ComplexSentence {
    private List<String> sentence;

    public ComplexSentence(List<String> sentence) {
        this.sentence = sentence;
    }

    public ComplexSentence() {
        sentence = new ArrayList<>();
    }

    public void addToSentence(String val){
        sentence.add(val);
    }

    public List<String> getSentence() {
        return sentence;
    }

    public void setSentence(List<String> sentence) {
        this.sentence = sentence;
    }

    @Override
    public String toString() {
        return "ComplexSentence{" +
                "sentence=" + sentence +
                '}';
    }
}
