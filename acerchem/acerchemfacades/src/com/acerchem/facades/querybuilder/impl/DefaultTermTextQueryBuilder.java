package com.acerchem.facades.querybuilder.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.querybuilder.impl.AbstractFreeTextQueryBuilder;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author Abel.li
 * @description
 * @contact abel0130@163.com
 * @date 2019-08-21
 */
public class DefaultTermTextQueryBuilder extends AbstractFreeTextQueryBuilder {

    private static final Logger LOG = Logger.getLogger(DefaultTermTextQueryBuilder.class);
    private String propertyName;
    private int boost;

    protected String getPropertyName()
    {
        return propertyName;
    }

    @Required
    public void setPropertyName(final String propertyName)
    {
        this.propertyName = propertyName;
    }

    protected int getBoost()
    {
        return boost;
    }

    @Required
    public void setBoost(final int boost)
    {
        this.boost = boost;
    }

    @Override
    public void addFreeTextQuery(final SearchQuery searchQuery, final String fullText, final String[] textWords)
    {
        final IndexedType indexedType = searchQuery.getIndexedType();
        if (indexedType != null)
        {
            final IndexedProperty indexedProperty = indexedType.getIndexedProperties().get(getPropertyName());
            if (indexedProperty != null)
            {
                this.addFreeTextQuery(searchQuery, indexedProperty, fullText, textWords, getBoost());
            }
        }
    }

    @Override
    protected void addFreeTextQuery(SearchQuery searchQuery, IndexedProperty indexedProperty, String fullText, String[] textWords, int boost){

        addFreeTextQuery(searchQuery, indexedProperty, fullText, boost * 2.0d);

        if (textWords != null && textWords.length > 1)
        {
            for (final String word : textWords)
            {
                addFreeTextQuery(searchQuery, indexedProperty, word, boost);
            }
        }

    }

    @Override
    protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String value,
                                    final double boost)
    {
        final String field = indexedProperty.getName();
        addFreeTextQuery(searchQuery, field, value.toLowerCase(), "", boost);
    }

}
