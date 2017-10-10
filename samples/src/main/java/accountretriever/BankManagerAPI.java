//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package accountretriever;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.commons.io.IOUtils;

public class BankManagerAPI {
    private static final String BANK_LIST_URL = "http://92328ac5.ngrok.io/BondINGOpenAPI/rest/bank-manager/banks";
    private static final String ACCOUNT_LIST_URL = "http://92328ac5.ngrok.io/BondINGOpenAPI/rest/hello/GetAllAccounts";

    public BankManagerAPI() {
    }

    public List<String> getBanksList() {
        HttpURLConnection connection = null;
        String result = null;
        ArrayList resultList = new ArrayList();

        try {
            URL url = new URL("http://92328ac5.ngrok.io/BondINGOpenAPI/rest/bank-manager/banks");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            StringWriter responseWriter = new StringWriter();
            IOUtils.copy(connection.getInputStream(), responseWriter);
            result = responseWriter.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Banks banks = (Banks)objectMapper.readValue(result, Banks.class);
            banks.getBanks().stream().forEach((e) -> {
                resultList.add(e.getId());
            });
        } catch (MalformedURLException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return resultList;
    }

    public Set<Account> getAccountsList(String bankId, String userName) {
        HttpURLConnection connection = null;
        String result = null;
        Object resultList = new HashSet();

        try {
            URL url = new URL("http://92328ac5.ngrok.io/BondINGOpenAPI/rest/hello/GetAllAccounts?bankId=" + bankId + "&username=" + userName);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            StringWriter responseWriter = new StringWriter();
            IOUtils.copy(connection.getInputStream(), responseWriter);
            result = responseWriter.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<Set<Account>> accountsTypes = new TypeReference<Set<Account>>() {
            };
            resultList = (Set)objectMapper.readValue(result, accountsTypes);
        } catch (MalformedURLException var14) {
            var14.printStackTrace();
        } catch (IOException var15) {
            var15.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return (Set)resultList;
    }

    public static void main(String[] args) {
        List<String> banksList = (new BankManagerAPI()).getBanksList();
        String speechText1 = String.format("you have %S banks. ", banksList.stream().count()) + String.join("; ", banksList).toUpperCase() + ". for which bank you would like to check balance?";
        System.out.println(speechText1);
        Set<Account> accountsList1 = (new BankManagerAPI()).getAccountsList("ing", "amzn1.ask.account.AFHULHF7WVS6O4N46LLVN2VYNRVEJMJRWXQGZJLURAZVHUEWUIFGPN6KVK52NQVXLK66QRY2OZ6CZRN47DWB355KGPMZWJCNLHCZOAQ5HYQD4KVHM22C5YPCKLIYZ5NCXBFMVVDXGHY4QTBWUJRSXJVJDRG5V5WF7DVYLZZILIYK3JWN4XLH6VM663RRYNFJQT2UX4XTPDEK44A");
        List<String> accountsList = new ArrayList();
        accountsList1.stream().forEach((e) -> {
            accountsList.add(e.getType());
        });
        String speechText = " You have " + String.join(" AND ", accountsList).toUpperCase() + " accounts. For which account you want to check balance?";
        System.out.println(speechText);
        accountsList1.stream().forEach((e) -> {
            System.out.println(e.getBalance().getAmount());
        });
    }
}
