package cz.rudypokorny.expense.validator;

import cz.rudypokorny.expense.entity.Rules;
import cz.rudypokorny.expense.model.Record;
import org.springframework.stereotype.Component;

@Component(value = "expenseValidator")
public class ExpenseValidator implements IValidator<Record> {

    @Override
    public Rules validateNew(Record entity) {
        Rules rules = new Rules();
        //TODO only BS rules
        if (entity == null) {
            return rules.broken(Rules.Messages.ENTITY_IS_NULL);
        }
        //FIXME these validation could be icked by hibernate manually
        if (entity.getAmount() == null) {
            rules.broken("amount is null");
        }
        if (entity.getAccount() == null || entity.getAccount().getId() == null) {
            rules.broken("account is null");
        }

        return rules;
    }


    @Override
    public Rules validateUpdate(Record entity) {
        return null;
    }

    @Override
    public Rules validateDelete(Record entity) {
        return null;
    }
}
