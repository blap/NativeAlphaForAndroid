package com.cylonid.nativealpha;

import com.cylonid.nativealpha.model.WebApp;
import com.cylonid.nativealpha.util.ShortcutIconUtils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class UtilUnitTests {

    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getWidthFromHTMLElementString() {
        assertEquals(192, ShortcutIconUtils.getWidthFromIcon("192x192"));
    }

    public void testShortcutHelper(String base_url, final String expected, final int result_index) {
        WebApp webapp = new WebApp(base_url, Integer.MAX_VALUE);
        ShortcutDialogFragment frag = ShortcutDialogFragment.newInstance(webapp);
        String[] result = frag.fetchWebappData();
        assertEquals(expected, result[result_index]);
    }

    @Test
    public void faviconFromWebManifest() {
        String manifestJson = "{\"icons\":[{\"src\":\"/assets/images/favicon-240x240.png\",\"sizes\":\"240x240\",\"type\":\"image/png\"}]}";
        String html = "<html><head><link rel=\"manifest\" href=\"/manifest.json\"></head><body></body></html>";

        mockWebServer.enqueue(new MockResponse().setBody(html));
        mockWebServer.enqueue(new MockResponse().setBody(manifestJson));

        String url = mockWebServer.url("/").toString();
        testShortcutHelper(url, url + "assets/images/favicon-240x240.png", IconFetchResult.FAVICON.index);
    }

    @Test
    public void faviconWithoutManifest() {
        String html = "<html><head><link rel=\"icon\" href=\"/favicon-128x128.png\"></head><body></body></html>";

        mockWebServer.enqueue(new MockResponse().setBody(html));

        String url = mockWebServer.url("/").toString();
        testShortcutHelper(url, url + "favicon-128x128.png", IconFetchResult.FAVICON.index);
    }

    @Test
    public void faviconNull() {
        String html = "<html><head></head><body></body></html>";
        mockWebServer.enqueue(new MockResponse().setBody(html));

        String url = mockWebServer.url("/").toString();
        testShortcutHelper(url, null, IconFetchResult.FAVICON.index);
    }

    @Test
    public void faviconNonExistingSite() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        String url = mockWebServer.url("/").toString();
        testShortcutHelper(url, null, IconFetchResult.FAVICON.index);
    }

    @Test
    public void getStartUrlFromWebManifest() {
        String manifestJson = "{\"start_url\":\"/app/desktop/#/login?pwa=1\",\"name\":\"TUGRAZonline Go\",\"icons\":[{\"src\":\"/assets/images/favicon-240x240.png\",\"sizes\":\"240x240\",\"type\":\"image/png\"}]}";
        String html = "<html><head><link rel=\"manifest\" href=\"/manifest.json\"></head><body></body></html>";

        mockWebServer.enqueue(new MockResponse().setBody(html));
        mockWebServer.enqueue(new MockResponse().setBody(manifestJson));

        String url = mockWebServer.url("/").toString();
        testShortcutHelper(url, url + "app/desktop/#/login?pwa=1", IconFetchResult.NEW_BASEURL.index);
    }

    @Test
    public void getWebAppTitleFromManifest() {
        String manifestJson = "{\"name\":\"TUGRAZonline Go\",\"start_url\":\"/app/desktop/#/login?pwa=1\",\"icons\":[{\"src\":\"/assets/images/favicon-240x240.png\",\"sizes\":\"240x240\",\"type\":\"image/png\"}]}";
        String html = "<html><head><link rel=\"manifest\" href=\"/manifest.json\"></head><body></body></html>";

        mockWebServer.enqueue(new MockResponse().setBody(html));
        mockWebServer.enqueue(new MockResponse().setBody(manifestJson));

        String url = mockWebServer.url("/").toString();
        testShortcutHelper(url, "TUGRAZonline Go", IconFetchResult.TITLE.index);
    }
}

