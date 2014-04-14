/*
 * Copyright 2014 Matthew Collins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.thinkofdeath.mapviewer.shared.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockFactory {

    boolean renderable = true;
    boolean solid = true;
    boolean collidable = true;
    boolean transparent = false;
    String texture;

    protected Map<String, BlockState> states = new HashMap<>();

    /**
     * Creates a block factory
     */
    public BlockFactory() {
    }

    /**
     * Returns all possible versions of the blocks from this
     * factory
     *
     * @return All possible blocks
     */
    public Block[] getBlocks() {
        if (states.size() > 0) {
            List<Map<String, Object>> stateList = new ArrayList<>();
            stateList.add(new HashMap<String, Object>());
            for (Map.Entry<String, BlockState> state : states.entrySet()) {
                List<Map<String, Object>> newStateList = new ArrayList<>();
                for (Object blockState : state.getValue().getStates()) {
                    for (Map<String, Object> stateMap : stateList) {
                        Map<String, Object> newStateMap = new HashMap<>(stateMap);
                        newStateMap.put(state.getKey(), blockState);
                        newStateList.add(newStateMap);
                    }
                }
                stateList = newStateList;
            }
            ArrayList<Block> blocks = new ArrayList<>();
            for (Map<String, Object> stateMap : stateList) {
                blocks.add(createBlock(stateMap));
            }
            return blocks.toArray(new Block[blocks.size()]);
        } else {
            return new Block[]{createBlock(new HashMap<String, Object>())};
        }
    }

    /**
     * Creates a block with the passed states
     *
     * @param states The block state
     * @return The created block
     */
    protected Block createBlock(Map<String, Object> states) {
        return new Block(this, states);
    }
}
