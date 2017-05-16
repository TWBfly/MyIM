package club.ah12530.myim.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import club.ah12530.myim.module.DBUtils;
import club.ah12530.myim.presenter.ContactFragmentPresenter;
import club.ah12530.myim.ui.view.ContactFragmentView;
import club.ah12530.myim.uitls.ThreadUitl;


public class ContactFragmentPresenterImpl implements ContactFragmentPresenter {
    private ContactFragmentView contactFragmentView;
    private List<String> contactList = new ArrayList<>();

    public ContactFragmentPresenterImpl(ContactFragmentView contactFragmentView) {
        this.contactFragmentView = contactFragmentView;
    }

    @Override
    public void initContacts() {
        //先在本地缓存
        //环信后台
        //更新本地缓存,刷新UI
        String currentUser = EMClient.getInstance().getCurrentUser();//当前登录的username
        List<String> contacts = DBUtils.getContacts(currentUser);//得到好友列表
        contactList.clear();
        contactList.addAll(contacts);
        contactFragmentView.onInitContacts(contactList);
        updateContactsFromServer(currentUser);

    }

    @Override
    public void updateContacts() {
        updateContactsFromServer(EMClient.getInstance().getCurrentUser());
    }

    private void updateContactsFromServer(final String currentUser) {
        //然后开辟子线程去环信后台获取当前用户的联系人
        ThreadUitl.runOnStubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> contactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    //排序,按字母A-Z排序
                    Collections.sort(contactsFromServer, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                    //更新本地的缓存，
                    DBUtils.updateContacts(currentUser,contactsFromServer);
                    contactList.clear();
                    contactList.addAll(contactsFromServer);
                    //通知View刷新UI
                    ThreadUitl.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            contactFragmentView.updateContacts(true,null);
                        }
                    });


                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    //获取失败
                    ThreadUitl.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            contactFragmentView.updateContacts(true,e.getMessage());
                        }
                    });

                }
            }
        });

    }
}
