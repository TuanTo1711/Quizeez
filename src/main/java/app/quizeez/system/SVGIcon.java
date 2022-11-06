package app.quizeez.system;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon.ColorFilter;
import java.awt.Color;

public class SVGIcon {

    public static ColorFilter setColor(Color color) {
        return new ColorFilter((t) -> color);
    }

    public static final FlatSVGIcon LOGO = new FlatSVGIcon("svg/hive-logo.svg", 40, 40);
    public static final FlatSVGIcon Showing_Menu = new FlatSVGIcon("svg/showing-item_menu.svg", 23, 23);
    public static final FlatSVGIcon Home_Item_Menu = new FlatSVGIcon("svg/home-item_menu.svg", 20, 20);
    public static final FlatSVGIcon Trends_Item_Menu = new FlatSVGIcon("svg/trending-item_menu.svg", 20, 20);
    public static final FlatSVGIcon NewsFeed_Item_Menu = new FlatSVGIcon("svg/feed-item_menu.svg", 20, 20);
    public static final FlatSVGIcon NewAndNote_Item_Menu = new FlatSVGIcon("svg/new-item_menu.svg", 20, 20);
    public static final FlatSVGIcon Calendar_Item_Menu = new FlatSVGIcon("svg/calendar-item_menu.svg", 20, 20);
    public static final FlatSVGIcon Event_Item_Menu = new FlatSVGIcon("svg/event-item_menu.svg", 20, 20);
    public static final FlatSVGIcon Favorite_Item_Menu = new FlatSVGIcon("svg/favorites-item_menu.svg", 20, 20);
    public static final FlatSVGIcon Chevron_right = new FlatSVGIcon("svg/chevron_right-profile_bottom.svg", 20, 20);

    public static final FlatSVGIcon MAGNIFIER_SEARCH = new FlatSVGIcon("svg/magnifier-search_input.svg", 15, 15);
    public static final FlatSVGIcon PREVIOUS_PAGE = new FlatSVGIcon("svg/previous-page.svg", 17, 17);
    public static final FlatSVGIcon REFRESH_PAGE = new FlatSVGIcon("svg/refresh-page.svg", 17, 17);
    public static final FlatSVGIcon NEXT_PAGE = new FlatSVGIcon("svg/next-page.svg", 17, 17);

    public static final FlatSVGIcon GMAIL_LOGIN = new FlatSVGIcon("svg/gmail-login.svg", 15, 15);
    public static final FlatSVGIcon MOON = new FlatSVGIcon("svg/moon.svg", 20, 20);
    public static final FlatSVGIcon SUN = new FlatSVGIcon("svg/sun.svg", 20, 20);

}
