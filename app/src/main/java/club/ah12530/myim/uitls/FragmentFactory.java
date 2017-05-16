package club.ah12530.myim.uitls;


import club.ah12530.myim.ui.fragment.BaseFragment;
import club.ah12530.myim.ui.fragment.ContactFragment;
import club.ah12530.myim.ui.fragment.ConversationFragment;
import club.ah12530.myim.ui.fragment.PluginFragment;

public class FragmentFactory {
    public static ContactFragment contactFragment;
    public static ConversationFragment conversationFragment;
    public static PluginFragment pluginFragment;

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                if (conversationFragment == null) {
                    conversationFragment = new ConversationFragment();
                }
                baseFragment = conversationFragment;
                break;

            case 1:
                if (contactFragment == null) {
                    contactFragment = new ContactFragment();
                }
                baseFragment = contactFragment;
                break;

            case 2:
                if (pluginFragment == null) {
                    pluginFragment = new PluginFragment();
                }
                baseFragment = pluginFragment;
                break;

            default:
                break;

        }
        return baseFragment;
    }
}
