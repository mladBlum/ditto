/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.base.model.headers;

import java.text.MessageFormat;
import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.base.model.exceptions.DittoHeaderInvalidException;

/**
 * This validator checks if a normalized CharSequence is equal to {@value #DITTO_CHANNEL_TWIN} or
 * {@value #DITTO_CHANNEL_LIVE}.
 * Normalized in this context means trimmed and converted to lower case.
 * Normalization is temporarily conducted by this class for validation only.
 *
 * @since 2.1.0
 */
@Immutable
final class DittoChannelValueValidator extends AbstractHeaderValueValidator {

    static final String DITTO_CHANNEL_TWIN = "twin";
    static final String DITTO_CHANNEL_LIVE = "live";

    private DittoChannelValueValidator() {
        super(String.class::equals);
    }

    /**
     * Returns an instance of {@code DittoChannelValueValidator}.
     *
     * @return the instance.
     */
    static DittoChannelValueValidator getInstance() {
        return new DittoChannelValueValidator();
    }

    @Override
    protected void validateValue(final HeaderDefinition definition, final CharSequence value) {
        final String normalizedValue = normalize(value);

        if (!DITTO_CHANNEL_TWIN.equals(normalizedValue) && !DITTO_CHANNEL_LIVE.equals(normalizedValue)) {
            throw DittoHeaderInvalidException.newInvalidTypeBuilder(definition, value, "ditto-channel")
                    .description(MessageFormat.format("The value must either be <{0}> or <{1}>.",
                            DITTO_CHANNEL_TWIN,
                            DITTO_CHANNEL_LIVE))
                    .build();
        }
    }

    private static String normalize(final CharSequence charSequence) {
        return charSequence.toString().trim().toLowerCase(Locale.ENGLISH);
    }

}
