//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package accountretriever;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import promoteoffers.PromoteOffersNoDelegateIntentHandler;

public class GuiceModule extends AbstractModule {
    public GuiceModule() {
    }

    protected void configure() {
        this.bind(IIntentHandler.class).annotatedWith(Names.named("GetAccountBalanceNewIntent")).to(GetAccountBalanceNoDelegateIntentHandler.class);
        this.bind(IIntentHandler.class).annotatedWith(Names.named("PromoteOffersIntent")).to(PromoteOffersNoDelegateIntentHandler.class);
    }
}
