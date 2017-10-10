//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package accountretriever;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletRequestHandlerException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.dialog.ConfirmationStatus;
import com.amazon.speech.speechlet.interfaces.dialog.DialogState;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetAccountBalanceNoDelegateIntentHandler implements IIntentHandler {
    public static final String BANK_ID = "bankId";
    public static final String ACCOUNT_TYPE = "accountType";
    private Logger logger = LoggerFactory.getLogger(GetAccountBalanceNoDelegateIntentHandler.class);
    private BankManagerAPI bankManagerAPI = new BankManagerAPI();

    public GetAccountBalanceNoDelegateIntentHandler() {
    }

    public SpeechletResponse handle(SpeechletRequestEnvelope<IntentRequest> request) throws SpeechletRequestHandlerException {
        Intent updatedIntent = ((IntentRequest) request.getRequest()).getIntent();
        SpeechletResponse response = null;
        if (((IntentRequest) request.getRequest()).getIntent().getName() == "") {
            ;
        }

        DialogState dialogState = ((IntentRequest) request.getRequest()).getDialogState() == null ? DialogState.STARTED : ((IntentRequest) request.getRequest()).getDialogState();
        this.logger.info("dialogState={}", dialogState);
        switch (dialogState) {
            case STARTED:
            default:
                this.logger.info("dialog state {}", DialogState.STARTED);
            case IN_PROGRESS:
                Slot slot = updatedIntent.getSlot("bankId");
                String userName;
                if (slot == null || Strings.isNullOrEmpty(slot.getValue()) || slot.getConfirmationStatus() == ConfirmationStatus.DENIED) {
                    this.logger.info("Dialog.ElicitSlot bankId");
                    List<String> banksList = this.bankManagerAPI.getBanksList();
                    userName = String.format("you have %S banks. ", banksList.stream().count()) + String.join("; ", banksList).toUpperCase() + ". for which bank you would like to check balance?";
                    this.logger.info("asking {}", userName);
                    response = SpeechletResponse.newElicitSlotResponse("bankId", updatedIntent, PlainTextOutputSpeech.builder().withText(userName).build(), Reprompt.builder().withPlainText("for which bank you want to check balance?").build());
                    return response;
                }

                slot = updatedIntent.getSlot("accountType");
                String bankId = updatedIntent.getSlot("bankId").getValue();
                userName = request.getSession().getUser().getUserId();
                this.logger.info("pulling account details for {}", userName);
                this.logger.info("bankId {}", updatedIntent.getSlot("bankId").getValue());
                String speechText = "for " + updatedIntent.getSlot("bankId").getValue();
                List<String> accountType = new ArrayList();
                new HashSet();
                Set<Account> accountsList;
                Balance balance;
                if (bankId.toLowerCase().replaceAll(" ", "").contains("ING".toLowerCase().replaceAll(" ", "")) && !bankId.toLowerCase().replaceAll(" ", "").startsWith("g")) {
                    accountsList = this.bankManagerAPI.getAccountsList("ing", userName);
                    accountsList.stream().forEach(e -> {
                        accountType.add(e.getType());
                    });
                    if (accountsList.stream().count() == 1L) {
                        balance = ((Account) accountsList.stream().findFirst().get()).getBalance();
                        speechText = "YOU HAVE " + ((String) accountType.get(0)).toUpperCase() + " AND THE BALANCE IS " + balance.getAmount() + " " + balance.getCurrency();
                        new PlainTextOutputSpeech();
                        response = SpeechletResponse.newTellResponse(PlainTextOutputSpeech.builder().withText(speechText).build());
                        return response;
                    }

                    speechText = speechText + " You have " + String.join(" AND ", accountType).toUpperCase() + " accounts. For which account you want to check balance?";
                } else if (bankId.toLowerCase().replaceAll(" ", "").contains("Gotham".toLowerCase().replaceAll(" ", ""))) {
                    accountsList = this.bankManagerAPI.getAccountsList("gotham", userName);
                    accountsList.stream().forEach((e) -> {
                        accountType.add(e.getType());
                    });
                    if (accountsList.stream().count() == 1L) {
                        balance = ((Account) accountsList.stream().findFirst().get()).getBalance();
                        speechText = "YOU HAVE " + ((String) accountType.get(0)).toUpperCase() + " AND THE BALANCE IS " + balance.getAmount() + " " + balance.getCurrency();
                        new PlainTextOutputSpeech();
                        response = SpeechletResponse.newTellResponse(PlainTextOutputSpeech.builder().withText(speechText).build());
                        return response;
                    }

                    speechText = speechText + " You have " + String.join(" AND ", accountType).toUpperCase() + " accounts. For which account you want to check balance?";
                } else {
                    if (!bankId.toLowerCase().replaceAll(" ", "").contains("Gringotts".toLowerCase().replaceAll(" ", ""))) {
                        return this.getDefaultSpeechletResponse();
                    }

                    accountsList = this.bankManagerAPI.getAccountsList("gotham", userName);
                    accountsList.stream().forEach((e) -> {
                        accountType.add(e.getType());
                    });
                    if (accountsList.stream().count() == 1L) {
                        balance = ((Account) accountsList.stream().findFirst().get()).getBalance();
                        speechText = "YOU HAVE " + ((String) accountType.get(0)).toUpperCase() + " AND THE BALANCE IS " + balance.getAmount() + " " + balance.getCurrency();
                        new PlainTextOutputSpeech();
                        response = SpeechletResponse.newTellResponse(PlainTextOutputSpeech.builder().withText(speechText).build());
                        return response;
                    }

                    speechText = speechText + " You have " + String.join(" AND ", accountType).toUpperCase() + " accounts. For which account you want to check balance?";
                }

                if (slot == null || Strings.isNullOrEmpty(slot.getValue())) {
                    this.logger.info("Dialog.ElicitSlot accountType");
                    response = SpeechletResponse.newElicitSlotResponse("accountType", updatedIntent, PlainTextOutputSpeech.builder().withText(speechText).build(), Reprompt.builder().withPlainText("for which account you want to check balance?").build());
                    return response;
                }

                this.logger.info("Check slots : bankid {} accounttype {}", updatedIntent.getSlot("bankId"), updatedIntent.getSlot("accountType"));
            case COMPLETED:
        }

        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        this.logger.info("Check slots : bankid {} accounttype {}", updatedIntent.getSlot("bankId").getValue(), updatedIntent.getSlot("accountType").getValue());
        outputSpeech.setText("your account balance is 1000 euros.");
        response = SpeechletResponse.newTellResponse(outputSpeech);
        return response;
    }

    private SpeechletResponse getDefaultSpeechletResponse() {
        String speechText = "You are not giving required data. Please try again";
        SimpleCard card = this.getSimpleCard(speechText);
        SsmlOutputSpeech speech = new SsmlOutputSpeech();
        speech.setSsml(speechText);
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);
        return SpeechletResponse.newAskResponse(repromptSpeech, reprompt, card);
    }

    private SimpleCard getSimpleCard(String speechText) {
        SimpleCard card = new SimpleCard();
        card.setTitle("BondING");
        card.setContent(speechText);
        return card;
    }
}
