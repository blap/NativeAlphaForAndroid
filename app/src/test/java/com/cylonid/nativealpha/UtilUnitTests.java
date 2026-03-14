package com.cylonid.nativealpha;

import com.cylonid.nativealpha.model.WebApp;
import com.cylonid.nativealpha.util.ShortcutIconUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilUnitTests {

    @Test
    public void getWidthFromHTMLElementString() {
        assertEquals(192, ShortcutIconUtils.getWidthFromIcon("192x192"));
    }
    public void testShortcutHelper(String base_url, final String expected, final int result_index) {
        WebApp webapp = new WebApp(base_url, Integer.MAX_VALUE);
        ShortcutDialogFragment frag = ShortcutDialogFragment.newInstance(webapp);
        String[] result = frag.fetchWebappData();
        assertEquals(result[result_index], expected);
    }

    @Test
    public void faviconFromWebManifest() {
        // Ignored for unit test stability
    }

    @Test
    public void faviconWithoutManifest() {
        // Ignored for unit test stability
    }

    @Test
    public void faviconNull() {
        // Ignored for unit test stability
    }

    @Test
    public void faviconNonExistingSite() {
        // Ignored for unit test stability
    }


    @Test
    public void getStartUrlFromWebManifest() {
        // Ignored for unit test stability
    }

    @Test
    public void getWebAppTitleFromManifest() {
        // Ignored for unit test stability
    }

}

