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
package org.openfast.template;

import java.io.InputStream;
import org.openfast.BitVectorReader;
import org.openfast.Context;
import org.openfast.FieldValue;
import org.openfast.IntegerValue;
import org.openfast.Message;
import org.openfast.QName;
import org.openfast.ScalarValue;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.template.operator.Operator;
import org.openfast.template.type.Type;

public class Define extends Group implements FieldSet {
    private static final long serialVersionUID = 1L;

    public Define(QName name, Field[] fields) {
        super(name, fields, false);
    }

    public boolean usesPresenceMap() {
        return true;
    }

    public Define(String name, Field[] fields) {
        this(new QName(name), fields);
    }

    /**
     * @param index
     *            The index to find the field
     * @return Returns the index of the field object
     */
    public Field getField(int index) {
        return fields[index];
    }

    /**
     * @return Returns the length of the fields as an int
     */
    public int getFieldCount() {
        return fields.length;
    }

    /**
     * Uses the superclasses encode method to encode the byte array - see
     * Group.java
     *
     * @param message
     *            The GroupValue object to be encoded
     * @param context
     *            The previous object to keep the data in sync
     * @return Returns a byte array of the encoded message
     */
    public byte[] encode(Message message, Context context) {
        return null;
    }

    /**
     * Decodes the inputStream and creates a new message that contains this
     * information
     *
     * @param in
     *            The inputStream to be decoded
     * @param templateId
     *            The templateID of the message
     * @param presenceMapReader
     *            The BitVector map of the Message
     * @param context
     *            The previous object to keep the data in sync
     * @return Returns a new message object with the newly decoded fieldValue
     */
    public Message decode(InputStream in, int templateId, BitVectorReader presenceMapReader, Context context) {
        return null;
    }

    /**
     * @return Returns the class of the message
     */
    public Class getValueType() {
        return null;
    }

    public String toString() {
        return name.getName();
    }

    /**
     * @return Creates a new Message object with the specified FieldValue and
     *         the passed string value
     */
    public FieldValue createValue(String value) {
        return null;
    }

    /**
     * @return Returns the field array
     */
    public Field[] getFields() {
        return fields;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof Define))
            return false;
        return equals((Define) obj);
    }

    private boolean equals(Define other) {
        if (!name.equals(other.name))
            return false;
        if (fields.length != other.fields.length)
            return false;
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].equals(other.fields[i]))
                return false;
        }
        return true;
    }

    public int hashCode() {
        int hashCode = (name != null) ? name.hashCode() : 0;
        for (int i = 0; i < fields.length; i++)
            hashCode += fields[i].hashCode();
        return hashCode;
    }
}
