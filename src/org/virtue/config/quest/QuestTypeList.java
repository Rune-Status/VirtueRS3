/**
 * Copyright (c) 2014 Virtue Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions\:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.virtue.config.quest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.cache.Archive;
import org.virtue.cache.ReferenceTable;
import org.virtue.cache.def.ConfigDecoder;
import org.virtue.config.Js5ConfigGroup;
import org.virtue.game.entity.player.var.VarDomain;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Kayla <skype:ashbysmith1996>
 * @author Sundays211
 * @since 21/11/2015
 */
public class QuestTypeList extends ConfigDecoder<QuestType> {

	/**
	 * The {@link Logger} instance
	 */
	private static Logger logger = LoggerFactory.getLogger(QuestTypeList.class);
	
	private static QuestTypeList instance;
	
	public static void init (ReferenceTable configTable, Archive archive) {
		instance = new QuestTypeList(configTable, archive);
		logger.info("Found "+instance.getCount()+" quest definitions.");
	}
	
	public QuestTypeList getInstance () {
		return instance;
	}

	private QuestTypeList(ReferenceTable configTable, Archive archive) {
		super(configTable, archive, Js5ConfigGroup.QUESTTYPE, QuestType.class);
	}

    public int getTotalQuestPoints(VarDomain varDomain) {
        int points = 0;
        for (int id = 0; id < getCount(); id++) {
            QuestType quest = list(id);
            if (quest.isCompleted(varDomain)) {
            	points += quest.questPoints;
            }
        }
        return points;
    }

}
