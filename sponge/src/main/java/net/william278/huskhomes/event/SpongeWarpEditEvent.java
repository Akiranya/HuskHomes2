/*
 * This file is part of HuskHomes, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.william278.huskhomes.event;

import net.william278.huskhomes.position.Warp;
import net.william278.huskhomes.user.CommandUser;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Event;

public class SpongeWarpEditEvent implements IWarpEditEvent, Event, Cancellable {

    private boolean cancelled = false;
    private final Warp warp;
    private final CommandUser editor;

    public SpongeWarpEditEvent(@NotNull Warp warp, @NotNull CommandUser editor) {
        this.warp = warp;
        this.editor = editor;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @NotNull
    @Override
    public Warp getWarp() {
        return warp;
    }

    @NotNull
    @Override
    public CommandUser getEditor() {
        return editor;
    }

    @Override
    public Cause cause() {
        return Cause.builder()
                .append(editor)
                .build();
    }

}
