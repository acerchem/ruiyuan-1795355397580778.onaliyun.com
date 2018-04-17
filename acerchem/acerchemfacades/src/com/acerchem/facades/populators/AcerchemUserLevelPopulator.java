package com.acerchem.facades.populators;

import de.hybris.platform.commercefacades.user.data.UserLevelData;
import de.hybris.platform.converters.Populator;
import org.springframework.util.Assert;
import com.acerchem.core.model.UserLevelModel;

/**
 * Converter implementation for {@link de.hybris.platform.core.model.user.UserModel} as source and
 * {@link de.hybris.platform.commercefacades.user.data.UserLevelData} as target type.
 */
public class AcerchemUserLevelPopulator implements Populator<UserLevelModel, UserLevelData>
{
	@Override
	public void populate(final UserLevelModel source, final UserLevelData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		
		target.setLevelName(source.getLevelName());
		target.setLevelCode(source.getLevelCode().getCode());
		target.setDiscount(source.getDiscount());
	}

}

