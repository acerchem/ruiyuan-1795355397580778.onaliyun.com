/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.acerchem.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This ValueProvider will provide a list of promotion codes associated with the product. This implementation uses only
 * the DefaultPromotionGroup.
 */
public class FactoryValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider
{
	private FieldNameProvider fieldNameProvider;

	protected FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{

			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			fieldValues.addAll(createFieldValues((ProductModel)model, indexConfig, indexedProperty));
			return fieldValues;
		}
		else
		{
			throw new FieldValueProviderException("Cannot get promotion codes of non-product item");
		}
	}

	protected List<FieldValue> createFieldValues(final ProductModel product, final IndexConfig indexConfig,
			final IndexedProperty indexedProperty)
	{
		String factory = product.getManufacturerName();
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		if (StringUtils.isNotEmpty(factory)) {
			addFieldValues(fieldValues, indexedProperty, null, factory);
		}
		return fieldValues;
	}

	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty,
			final LanguageModel language, final Object value)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty,
				language == null ? null : language.getIsocode());
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
	}
}
