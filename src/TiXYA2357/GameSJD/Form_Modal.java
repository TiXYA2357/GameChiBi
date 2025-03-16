package TiXYA2357.GameSJD;

import cn.nukkit.Player;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import lombok.Data;

import static TiXYA2357.GameSJD.Lib.Async;


@Data
public class Form_Modal {
    private final FormWindowModal form;

    private Runnable truer;
    private Runnable falser;
    private Runnable close;


    public Form_Modal(String title, String content, String trueButtonText, String falseButtonText) {
        this.form = new FormWindowModal(title, content, trueButtonText, falseButtonText);
    }

    public static void tipsModal(Player player, String content, FormWindow form) {
        Form_Modal modal = new Form_Modal("提示", content, "返回", "关闭");
        modal.setTruer(() -> player.showFormWindow(form));
        modal.asyncShow(player);
    }

    public static void confirmModal(Player player, String content, FormWindow form, Runnable truer) {
        Form_Modal form1 = new Form_Modal("需要确认", content, "确认", "返回");
        form1.setTruer(truer);
        form1.setFalser(() -> player.showFormWindow(form));
        form1.asyncShow(player);
    }
    public static void confirmModal(Player player, String content, Runnable truer) {
        Form_Modal form1 = new Form_Modal("需要确认", content, "确认", "取消");
        form1.setTruer(truer);
        form1.asyncShow(player);
    }
    public void show(Player player) {
        this.form.addHandler(FormResponseHandler.withoutPlayer(ignored -> processReturns()));
        player.showFormWindow(this.form);
    }



    public void asyncShow(Player player) {
        this.form.addHandler(FormResponseHandler.withoutPlayer(ignored -> Async(this::processReturns)));
        player.showFormWindow(this.form);
    }

    private void processReturns() {
        if (this.form.wasClosed()) {
            if (this.close != null) {
                this.close.run();
            }
            return;
        }
        if (this.form.getResponse().getClickedButtonId() == 0) {
            if (this.truer != null) {
                this.truer.run();
            }
        } else {
            if (this.falser != null) {
                this.falser.run();
            }
        }
    }
}