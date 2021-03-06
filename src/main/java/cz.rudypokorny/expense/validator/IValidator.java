package cz.rudypokorny.expense.validator;

import cz.rudypokorny.expense.entity.Rules;
import cz.rudypokorny.expense.entity.Validable;

/**
 * Created by Rudolf on 11/07/2017.
 */
public interface IValidator<T extends Validable> {

    Rules validateNew(T entity);

    Rules validateUpdate(T entity);

    Rules validateDelete(T entity);
}
