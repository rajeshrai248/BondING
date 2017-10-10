

package promoteoffers;

import accountretriever.BankManagerAPI;
import accountretriever.IIntentHandler;
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
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PromoteOffersNoDelegateIntentHandler implements IIntentHandler {
    public static final String CREDIT_LIMIT = "creditLimit";
    public static final String CARD_TYPE = "cardType";
    public static final String INFO_FLAG = "moreInfoFlag";
    private Logger logger = LoggerFactory.getLogger(PromoteOffersNoDelegateIntentHandler.class);

    public PromoteOffersNoDelegateIntentHandler() {
    }

    public SpeechletResponse handle(SpeechletRequestEnvelope<IntentRequest> request) throws SpeechletRequestHandlerException {
        Intent updatedIntent = (request.getRequest()).getIntent();
        SpeechletResponse response = null;
        DialogState dialogState = (request.getRequest()).getDialogState() == null ? DialogState.STARTED : ((IntentRequest)request.getRequest()).getDialogState();
        this.logger.info("dialogState={}", dialogState);
        Slot slot;
        switch(dialogState) {
            case STARTED:
            default:
                this.logger.info("dialog state {}", DialogState.STARTED);
                this.logger.info("bank id {}", (new BankManagerAPI()).getBanksList());
            case IN_PROGRESS:
                slot = updatedIntent.getSlot(CREDIT_LIMIT);
                this.logger.info("slot credit limit {}", slot);
                if (slot == null || Strings.isNullOrEmpty(slot.getValue()) || slot.getConfirmationStatus() == ConfirmationStatus.DENIED) {
                    this.logger.info("Dialog.ElicitSlot creditLimit");
                    response = SpeechletResponse.newElicitSlotResponse(CREDIT_LIMIT, updatedIntent, PlainTextOutputSpeech.builder().withText("how much limit you would require?").build(), Reprompt.builder().withPlainText("what limit are you looking for?").build());
                    this.logger.info("asking for credit limit");
                    break;
                }
            case COMPLETED:
                Slot moreInfo = updatedIntent.getSlot("moreInfoFlag");
                this.logger.info("checking for more information on card for limit {}", updatedIntent.getSlot(CREDIT_LIMIT).getValue());
                this.logger.info("confirmation status for more info {}", updatedIntent.getConfirmationStatus());
                this.logger.info("more info flag {}", moreInfo);
                if ((moreInfo == null || Strings.isNullOrEmpty(moreInfo.getValue())) && updatedIntent.getConfirmationStatus().equals(ConfirmationStatus.NONE)) {
                    response = SpeechletResponse.newConfirmIntentResponse(updatedIntent, PlainTextOutputSpeech.builder().withText("Ok! I have 3 credit card options. SILVER; GOLD; PLATINUM. would you like to know more about them?").build(), (Reprompt)null);
                    this.logger.info("confirming for card details");
                } else if ((moreInfo == null || Strings.isNullOrEmpty(moreInfo.getValue())) && updatedIntent.getConfirmationStatus().equals(ConfirmationStatus.CONFIRMED)) {
                    int creditLimit = Integer.parseInt(updatedIntent.getSlot(CREDIT_LIMIT).getValue());
                    moreInfo.setValue("false");
                    updatedIntent.setSlot(moreInfo);
                    response = SpeechletResponse.newConfirmIntentResponse(updatedIntent, PlainTextOutputSpeech.builder().withText("SILVER card has a limit of " + (creditLimit + 100) + " euros. " + "GOLD has a limit of " + (creditLimit + 500) + " euros. " + "PLATINUM has a limit of " + (creditLimit + 1000) + " euros. " + "would you like to apply?").build(), Reprompt.builder().withPlainText("please confirm if you would like to apply. ").build());
                    this.logger.info("confirming for card application");
                } else {
                    slot = updatedIntent.getSlot(CARD_TYPE);
                    if ((moreInfo == null || Strings.isNullOrEmpty(moreInfo.getValue())) && !Strings.isNullOrEmpty(moreInfo.getValue()) && (slot == null || Strings.isNullOrEmpty(slot.getValue())) && Strings.isNullOrEmpty(moreInfo.getValue()) && updatedIntent.getConfirmationStatus().equals(ConfirmationStatus.CONFIRMED)) {
                        this.logger.info("Dialog.ElicitSlot cardType");
                        response = SpeechletResponse.newElicitSlotResponse(CARD_TYPE, updatedIntent, PlainTextOutputSpeech.builder().withText("which card would you like to apply?").build(), (Reprompt)null);
                    } else if (updatedIntent.getConfirmationStatus().equals(ConfirmationStatus.DENIED)) {
                        response = SpeechletResponse.newTellResponse(PlainTextOutputSpeech.builder().withText("OK!").build());
                    } else {
                        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                        slot = updatedIntent.getSlot(CARD_TYPE);
                        this.logger.info("card type slot {}", slot);
                        new String();
                        String speechText;
                        if (slot != null && !Strings.isNullOrEmpty(slot.getValue())) {
                            this.logger.info("card type {}", slot.getValue());
                            speechText = "your application is received successfully. you would receive further notification from Bank. ";
                        } else {
                            speechText = "Ok! I will not apply for credit card";
                        }

                        this.logger.info("Check slots : credit limit  {} card type {}", updatedIntent.getSlot(CREDIT_LIMIT).getValue(), updatedIntent.getSlot(CARD_TYPE));
                        if (slot != null && !Strings.isNullOrEmpty(slot.getValue())) {
                            this.logger.info("card type received {}", slot.getValue());
                        }

                        outputSpeech.setText(speechText);
                        this.logger.info("finished execution with {}", speechText);
                        response = SpeechletResponse.newTellResponse(outputSpeech);
                    }
                }
        }

        return response;
    }
}
