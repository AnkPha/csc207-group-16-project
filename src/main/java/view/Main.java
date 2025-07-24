package view;

import interface_adapter.search_nearby_locations.SearchViewModel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import data_access.SearchLocationNearbyDataAccessObject;
import use_case.search_nearby_locations.SearchLocationsNearbyInteractor;
import use_case.search_nearby_locations.SearchLocationsNearbyInputData;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyController;
import use_case.search_nearby_locations.SearchLocationsNearbyOutputData;
import interface_adapter.search_nearby_locations.SearchLocationsNearbyPresenter;
public class Main {
    public static void main(String[] args) {
        SearchViewModel viewModel = new SearchViewModel();
        SearchLocationNearbyDataAccessObject search = new SearchLocationNearbyDataAccessObject();
        SearchLocationsNearbyPresenter presenter = new SearchLocationsNearbyPresenter(viewModel);
        SearchLocationsNearbyInteractor interactor = new SearchLocationsNearbyInteractor(search, presenter);
        SearchLocationsNearbyController controller = new SearchLocationsNearbyController(interactor);

        // Run GUI code on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create the main window (frame)
            JFrame frame = new JFrame("My Panel Test");

            // Set default close operation so the program exits when the window is closed
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create an instance of your panel class
            SearchPanel panel = new SearchPanel(viewModel);
            panel.setSearchLocationsController(controller);
            // Add your panel to the frame's content pane
            frame.getContentPane().add(panel);

            // Set the size of the window
            frame.setSize(800, 600);

            // Center the window on screen
            frame.setLocationRelativeTo(null);

            // Make the window visible
            frame.setVisible(true);

//            "1 Dundas St E, Toronto, Canada",
            // 100 Queen St W, Toronto, ON, Canada
            // 100, 500
        });
    }
}

//java.net.SocketTimeoutException: timeout
//at okio.SocketAsyncTimeout.newTimeoutException(JvmOkio.kt:146)
//at okio.AsyncTimeout.access$newTimeoutException(AsyncTimeout.kt:161)
//at okio.AsyncTimeout$source$1.read(AsyncTimeout.kt:339)
//at okio.RealBufferedSource.indexOf(RealBufferedSource.kt:430)
//at okio.RealBufferedSource.readUtf8LineStrict(RealBufferedSource.kt:323)
//at okhttp3.internal.http1.HeadersReader.readLine(HeadersReader.kt:29)
//at okhttp3.internal.http1.Http1ExchangeCodec.readResponseHeaders(Http1ExchangeCodec.kt:180)
//at okhttp3.internal.connection.Exchange.readResponseHeaders(Exchange.kt:110)
//at okhttp3.internal.http.CallServerInterceptor.intercept(CallServerInterceptor.kt:93)
//at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
//at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.kt:34)
//at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
//at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.kt:95)
//at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
//at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.kt:83)
//at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
//at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.kt:76)
//at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
//at okhttp3.internal.connection.RealCall.getResponseWithInterceptorChain$okhttp(RealCall.kt:201)
//at okhttp3.internal.connection.RealCall.execute(RealCall.kt:154)
//at data_access.OverPassAPI.getNearbyRestaurants(OverPassAPI.java:30)
//at data_access.SearchLocationNearbyDataAccessObject.getNearbyRestaurants(SearchLocationNearbyDataAccessObject.java:40)
//at use_case.search_nearby_locations.SearchLocationsNearbyInteractor.execute(SearchLocationsNearbyInteractor.java:24)
//at interface_adapter.search_nearby_locations.SearchLocationsNearbyController.execute(SearchLocationsNearbyController.java:14)
//at view.SearchPanel.lambda$new$0(SearchPanel.java:115)
//at java.desktop/javax.swing.AbstractButton.fireActionPerformed(AbstractButton.java:1972)
//at java.desktop/javax.swing.AbstractButton$Handler.actionPerformed(AbstractButton.java:2313)
//at java.desktop/javax.swing.DefaultButtonModel.fireActionPerformed(DefaultButtonModel.java:405)
//at java.desktop/javax.swing.DefaultButtonModel.setPressed(DefaultButtonModel.java:262)
//at java.desktop/javax.swing.plaf.basic.BasicButtonListener.mouseReleased(BasicButtonListener.java:279)
//at java.desktop/java.awt.Component.processMouseEvent(Component.java:6626)
//at java.desktop/javax.swing.JComponent.processMouseEvent(JComponent.java:3389)
//at java.desktop/java.awt.Component.processEvent(Component.java:6391)
//at java.desktop/java.awt.Container.processEvent(Container.java:2266)
//at java.desktop/java.awt.Component.dispatchEventImpl(Component.java:5001)
//at java.desktop/java.awt.Container.dispatchEventImpl(Container.java:2324)
//at java.desktop/java.awt.Component.dispatchEvent(Component.java:4833)
//at java.desktop/java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4948)
//at java.desktop/java.awt.LightweightDispatcher.processMouseEvent(Container.java:4575)
//at java.desktop/java.awt.LightweightDispatcher.dispatchEvent(Container.java:4516)
//at java.desktop/java.awt.Container.dispatchEventImpl(Container.java:2310)
//at java.desktop/java.awt.Window.dispatchEventImpl(Window.java:2780)
//at java.desktop/java.awt.Component.dispatchEvent(Component.java:4833)
//at java.desktop/java.awt.EventQueue.dispatchEventImpl(EventQueue.java:775)
//at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:720)
//at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:714)
//at java.base/java.security.AccessController.doPrivileged(AccessController.java:399)
//at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:86)
//at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:97)
//at java.desktop/java.awt.EventQueue$5.run(EventQueue.java:747)
//at java.desktop/java.awt.EventQueue$5.run(EventQueue.java:745)
//at java.base/java.security.AccessController.doPrivileged(AccessController.java:399)
//at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:86)
//at java.desktop/java.awt.EventQueue.dispatchEvent(EventQueue.java:744)
//at java.desktop/java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:203)
//at java.desktop/java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:124)
//at java.desktop/java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:113)
//at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:109)
//at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
//at java.desktop/java.awt.EventDispatchThread.run(EventDispatchThread.java:90)
//Caused by: java.net.SocketException: Socket closed
//at java.base/sun.nio.ch.NioSocketImpl.endRead(NioSocketImpl.java:253)
//at java.base/sun.nio.ch.NioSocketImpl.implRead(NioSocketImpl.java:332)
//at java.base/sun.nio.ch.NioSocketImpl.read(NioSocketImpl.java:355)
//at java.base/sun.nio.ch.NioSocketImpl$1.read(NioSocketImpl.java:808)
//at java.base/java.net.Socket$SocketInputStream.read(Socket.java:966)
//at java.base/sun.security.ssl.SSLSocketInputRecord.read(SSLSocketInputRecord.java:484)
//at java.base/sun.security.ssl.SSLSocketInputRecord.readHeader(SSLSocketInputRecord.java:478)
//at java.base/sun.security.ssl.SSLSocketInputRecord.bytesInCompletePacket(SSLSocketInputRecord.java:70)

