package xydproject.pluginutil.date;

public interface OnTurnPageListener {

    void OnLeftUp(int today, int month, int year);
    void OnLeftDown(int today, int month, int year);

    void OnRightUp(int today, int month, int year);
    void OnRightDown(int today, int month, int year);
}
