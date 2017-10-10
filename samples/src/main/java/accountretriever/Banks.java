//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package accountretriever;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class Banks {
    @JsonProperty("banks")
    private List<Bank> banks;

    public Banks() {
    }

    public List<Bank> getBanks() {
        return this.banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }
}
