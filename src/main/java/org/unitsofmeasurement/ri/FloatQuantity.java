package org.unitsofmeasurement.ri;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;

class FloatQuantity<T extends Quantity<T>> extends AbstractQuantity<T> {

	final float value;

    public FloatQuantity(float value, Unit<T> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    // Implements AbstractMeasurement
    public double doubleValue(Unit<T> unit) {
        return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
    }

	public long longValue(Unit<T> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

	@Override
	public AbstractQuantity<T> add(Measurement<T, Number> that) {
		return of(value + that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public AbstractQuantity<T> substract(Measurement<T, Number> that) {
		return of(value - that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<T> multiply(Measurement<?, Number> that) {
		return (AbstractQuantity<T>) of(value * that.getValue().floatValue(), 
				getUnit().multiply(that.getUnit()));
	}

	@Override
	public Measurement<?, Number> multiply(Number that) {
		return of(value * that.floatValue(), 
				getUnit().multiply(that.doubleValue()));
	}

	@Override
	public Quantity<?> divide(Measurement<?, Number> that) {
		return of(value / that.getValue().floatValue(), getUnit().divide(that.getUnit()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Quantity<T> inverse() {
		return (AbstractQuantity<T>) of(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}

	@Override
	public Measurement<?, Number> divide(Number that) {
		return of(value / that.floatValue(), getUnit());
	}
}