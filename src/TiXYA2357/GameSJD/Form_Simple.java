package TiXYA2357.GameSJD;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowSimple;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static TiXYA2357.GameSJD.Lib.Async;


@Data
public class Form_Simple {
    private final FormWindowSimple form;
    private final List<Runnable> buttons = new ArrayList<>();
    private Runnable close;

    public Form_Simple(String title, String content) {
        this.form = new FormWindowSimple(title, content);
    }

    public void add(String text) {
        this.buttons.add(() -> {});
        this.form.addButton(new ElementButton(text));
    }
    public void add(String text, Runnable runnable) {
        this.buttons.add(runnable);
        this.form.addButton(new ElementButton(text));
    }

    public void add(String text, String img, Runnable runnable) {
        String type = img.startsWith("http") ? "url" : "path";
        this.buttons.add(runnable);
        this.form.addButton(new ElementButton(text, new ElementButtonImageData(type, img)));
    }
    public int getClickedId(){
        return this.form.getResponse().getClickedButtonId();
    }

    public void show(Player player) {
        this.form.addHandler(FormResponseHandler.withoutPlayer(ignored -> processReturns()));
        player.showFormWindow(this.form);
    }

    private void processReturns() {
        if (this.form.wasClosed()) {
            if (this.close != null) {
                this.close.run();
            }
            return;
        }
        this.buttons.get(form.getResponse().getClickedButtonId()).run();
    }

    public void asyncShow(Player player) {
        this.form.addHandler(FormResponseHandler.withoutPlayer(ignored -> Async(this::processReturns)));
        player.showFormWindow(this.form);
    }
}