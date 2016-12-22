package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ChannelDao {
    public static void main(String[] args) throws Exception {
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(1, "me.mvp.greendao");
        addNote(schema);
        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema,  "./app/src/main/java-gen");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity entity = schema.addEntity("NewsChannelTable");
        // 你也可以重新给表命名
        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        /**
         * 频道名称
         */
        entity.addStringProperty("newsChannelName").notNull().primaryKey().index();
        /**
         * 频道id
         */
        entity.addStringProperty("newsChannelId").notNull();
        /**
         * 频道类型
         */
        entity.addStringProperty("newsChannelType").notNull();
        /**
         * 选中的频道
         */
        entity.addBooleanProperty("newsChannelSelect").notNull();
        /**
         * 频道的排序位置
         */
        entity.addIntProperty("newsChannelIndex").notNull();
        /**
         * 频道是否是固定的
         */
        entity.addBooleanProperty("newsChannelFixed");
    }
}