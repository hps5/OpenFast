/*
The contents of this file are subject to the Mozilla Public License
Version 1.1 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at
http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
License for the specific language governing rights and limitations
under the License.

The Original Code is OpenFAST.

The Initial Developer of the Original Code is The LaSalle Technology
Group, LLC.  Portions created by The LaSalle Technology Group, LLC
are Copyright (C) The LaSalle Technology Group, LLC. All Rights Reserved.

Contributor(s): Jacob Northey <jacob@lasalletech.com>
                Craig Otis <cotis@lasalletech.com>
 */
package org.openfast.codec.operator;

import java.nio.ByteBuffer;
import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.template.operator.DictionaryOperator;

public final class IncrementIntegerCodec extends DictionaryOperatorIntegerCodec implements FieldCodec {
    private static final long serialVersionUID = 1L;

    public IncrementIntegerCodec(DictionaryEntry dictionaryEntry, DictionaryOperator operator, IntegerCodec integerCodec) {
        super(dictionaryEntry, operator, integerCodec);
    }

    public void decode(EObject object, int index, ByteBuffer buffer, Context context) {
        if (integerCodec.isNull(buffer))
            return;
        int value = integerCodec.decode(buffer);
        dictionaryEntry.set(value);
        object.set(index, value);
    }

    public void decodeEmpty(EObject object, int index, Context context) {
        if (dictionaryEntry.isNull()) {
            // leave object value set to null
            dictionaryEntry.setNull();
        } else if (!dictionaryEntry.isDefined()) {
            if (operator.hasDefaultValue()) {
                object.set(index, initialValue);
                dictionaryEntry.set(initialValue);
            } else {
//                if (!scalar.isOptional()) {
//                    throw new IllegalStateException("Field with operator increment must send a value if no previous value existed.");
//                } else {
                    // leave object value set to null
                    dictionaryEntry.setNull();
//                }
            }
        } else {
            int previousValue = dictionaryEntry.getInt();
            object.set(index, previousValue + 1);
            dictionaryEntry.set(previousValue + 1);
        }
    }

    public int getLength(ByteBuffer buffer) {
        return integerCodec.getLength(buffer);
    }

    public void encode(EObject object, int index, ByteBuffer buffer, Context context) {
        if (!object.isDefined(index)) {
            if (!dictionaryEntry.isNull())
                encodeNull(buffer);
            return;
        }
        int value = object.getInt(index);
        if (dictionaryEntry.isNull()) {
            dictionaryEntry.set(value);
            integerCodec.encode(buffer, value);
            return;
        }
        if (!dictionaryEntry.isDefined()) {
            if (!operator.hasDefaultValue()) {
                dictionaryEntry.set(value);
                integerCodec.encode(buffer, value);
            } else if (operator.hasDefaultValue() && value == initialValue) {
                dictionaryEntry.set(value);
            } else {
                integerCodec.encode(buffer, value);
            }
            return;
        }
        int previousValue = dictionaryEntry.getInt();
        if (value == previousValue + 1) {
            dictionaryEntry.set(value);
            return;
        }

        dictionaryEntry.set(value);
        integerCodec.encode(buffer, value);
    }

    private void encodeNull(ByteBuffer buffer) {
        buffer.put(Fast.NULL);
        dictionaryEntry.setNull();
    }
}