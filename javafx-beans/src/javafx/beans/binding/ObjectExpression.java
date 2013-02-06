/*
 * Copyright (c) 2010, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package javafx.beans.binding;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.sun.javafx.collections.annotations.ReturnsUnmodifiableCollection;

/**
 * A {@code ObjectExpression} is a
 * {@link javafx.beans.value.ObservableObjectValue} plus additional convenience
 * methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code ObjectExpression} has to implement the method
 * {@link javafx.beans.value.ObservableObjectValue#get()}, which provides the
 * actual value of this expression.
 */
public abstract class ObjectExpression<T> implements ObservableObjectValue<T> {

    @Override
    public T getValue() {
        return get();
    }

    /**
     * Returns an {@code ObjectExpression} that wraps an
     * {@link javafx.beans.value.ObservableObjectValue}. If the
     * {@code ObservableObjectValue} is already an {@code ObjectExpression}, it
     * will be returned. Otherwise a new
     * {@link javafx.beans.binding.ObjectBinding} is created that is bound to
     * the {@code ObservableObjectValue}.
     * 
     * @param value
     *            The source {@code ObservableObjectValue}
     * @return A {@code ObjectExpression} that wraps the
     *         {@code ObservableObjectValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static <T> ObjectExpression<T> objectExpression(
            final ObservableObjectValue<T> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return value instanceof ObjectExpression ? (ObjectExpression<T>) value
                : new ObjectBinding<T>() {
                    {
                        super.bind(value);
                    }

                    @Override
                    public void dispose() {
                        super.unbind(value);
                    }

                    @Override
                    protected T computeValue() {
                        return value.get();
                    }

                    @Override
                    @ReturnsUnmodifiableCollection
                    public ObservableList<ObservableObjectValue<T>> getDependencies() {
                        return FXCollections.singletonObservableList(value);
                    }
                };
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this and
     * another {@link javafx.beans.value.ObservableObjectValue} are equal.
     * 
     * @param other
     *            the other {@code ObservableObjectValue}
     * @return the new {@code BooleanExpression}
     * @throws NullPointerException
     *             if {@code other} is {@code null}
     */
    public BooleanBinding isEqualTo(final ObservableObjectValue<?> other) {
        return Bindings.equal(this, other);
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this
     * {@code ObjectExpression} is equal to a constant value.
     * 
     * @param other
     *            the constant value
     * @return the new {@code BooleanExpression}
     */
    public BooleanBinding isEqualTo(final Object other) {
        return Bindings.equal(this, other);
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this and
     * another {@link javafx.beans.value.ObservableObjectValue} are not equal.
     * 
     * @param other
     *            the other {@code ObservableObjectValue}
     * @return the new {@code BooleanExpression}
     * @throws NullPointerException
     *             if {@code other} is {@code null}
     */
    public BooleanBinding isNotEqualTo(final ObservableObjectValue<?> other) {
        return Bindings.notEqual(this, other);
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this
     * {@code ObjectExpression} is not equal to a constant value.
     * 
     * @param other
     *            the constant value
     * @return the new {@code BooleanExpression}
     */
    public BooleanBinding isNotEqualTo(final Object other) {
        return Bindings.notEqual(this, other);
    }
    
    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this
     * {@code ObjectExpression} is {@code null}.
     * 
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNull() {
        return Bindings.isNull(this);
    }
    
    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this
     * {@code ObjectExpression} is not {@code null}.
     * 
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotNull() {
        return Bindings.isNotNull(this);
    }
}
