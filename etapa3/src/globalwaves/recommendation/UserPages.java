package globalwaves.recommendation;

import globalwaves.searchbar.SelectResults;

import java.util.ArrayList;

public final  class UserPages {

    private String username;
    private ArrayList<SelectResults> pages;
    private Integer pageIdx;
    public UserPages() {
    }

    public UserPages(final String username) {
        this.username = username;
        this.pages = new ArrayList<>();
        SelectResults firstPage = new SelectResults();
        firstPage.setUsername(username);
        firstPage.setLastCommand("Home");
        this.pageIdx = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public ArrayList<SelectResults> getPages() {
        return pages;
    }

    public void setPages(final ArrayList<SelectResults> pages) {
        this.pages = pages;
    }

    public Integer getPageIdx() {
        return pageIdx;
    }

    public void setPageIdx(final Integer pageIdx) {
        this.pageIdx = pageIdx;
    }
}
