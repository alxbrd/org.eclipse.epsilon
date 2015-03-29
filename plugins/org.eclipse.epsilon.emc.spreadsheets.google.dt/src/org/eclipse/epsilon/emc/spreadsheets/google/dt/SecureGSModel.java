package org.eclipse.epsilon.emc.spreadsheets.google.dt;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.spreadsheets.google.GSModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureGSModel extends GSModel
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SecureGSModel.class);

	@Override
	public void load(final StringProperties properties, final IRelativePathResolver resolver)
			throws EolModelLoadingException
	{
		try
		{
			final String password = this.loadPassword(properties);
			if (password == null)
			{
				final String message = "Password may not be null";
				LOGGER.error(message);
				throw new Exception(message);
			}

			properties.put(GSModel.GOOGLE_PASSWORD, password);

			super.load(properties, resolver);
		}
		catch (Exception e)
		{
			LOGGER.debug(e.getMessage());
			throw new EolModelLoadingException(e, this);
		}
	}

	public String loadPassword(final StringProperties properties) throws StorageException
	{
		final ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		if (preferences != null)
		{
			// Password is stored in the vault associated with the Google username
			final String username = properties.getProperty(GSModel.GOOGLE_USERNAME);
			if (preferences.nodeExists(username))
			{
				final ISecurePreferences node = preferences.node(username);
				return node.get(GSModel.GOOGLE_PASSWORD, null);
			}
			else
			{
				final String message = "Equinox Security could not find Google account password for '" + username + "'";
				LOGGER.error(message);
				throw new RuntimeException(message);
			}
		}
		else
		{
			final String message = "Equinox Security was unable to create secure preferences using default location";
			LOGGER.error(message);
			throw new RuntimeException(message);
		}
	}

}
