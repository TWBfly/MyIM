package club.ah12530.myim.ui.view;


import java.util.List;

public interface ContactFragmentView {
    void onInitContacts(List<String> contactList);

    void updateContacts(boolean b, String o);
}
