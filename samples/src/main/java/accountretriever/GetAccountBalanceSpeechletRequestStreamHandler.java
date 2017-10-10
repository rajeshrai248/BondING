/*
 * Copyright 2016-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 * this file except in compliance with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package accountretriever;

import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.inject.Guice;
import com.google.inject.Module;

import java.util.HashSet;
import java.util.Set;

/**
 * This class could be the handler for an AWS Lambda function powering an Alexa Skills Kit
 * experience. To do this, simply set the handler field in the AWS Lambda console to
 * "plantripdialog.PromoteOffersSpeechletRequestStreamHandler" For this to work, you'll also need to
 * build this project using the {@code lambda-compile} Ant task and upload the resulting zip file to
 * power your function.
 */
public class GetAccountBalanceSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<>();
        supportedApplicationIds.add("amzn1.ask.skill.696a87e3-8511-464a-8539-eacc8ab283d5");
    }

    public GetAccountBalanceSpeechletRequestStreamHandler() {
        super((SpeechletV2) Guice.createInjector(new Module[]{new GuiceModule()}).getInstance(GetAccountBalanceDialogSpeechlet.class), supportedApplicationIds);
    }
}
