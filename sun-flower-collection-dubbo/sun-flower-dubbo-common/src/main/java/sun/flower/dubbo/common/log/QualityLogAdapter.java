package sun.flower.dubbo.common.log;

import org.apache.dubbo.common.logger.Level;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerAdapter;

import java.io.File;

/**
 * @Desc: 质量日志
 * @Author: chenbo
 * @Date: 2019/8/27 10:56
 **/
public class QualityLogAdapter implements LoggerAdapter {

    @Override
    public Logger getLogger(Class<?> key) {
        return null;
    }

    @Override
    public Logger getLogger(String key) {
        return null;
    }

    @Override
    public Level getLevel() {
        return null;
    }

    @Override
    public void setLevel(Level level) {

    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public void setFile(File file) {

    }

}
