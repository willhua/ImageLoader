package com.willhua.imageloader;

public class LoaderConfig {
    private int mMaxMemeryCacheSize; //unit is kb
    private int mMaxDiskCacheSize;
    private boolean mUseMemeryCache;
    private boolean mUseDiskCache;
    
    public static LoaderConfig defaultConfig(){
        return new ConfigBuilder().build();
    }
    
    private LoaderConfig (ConfigBuilder builder){
        boolean b = builder.mUseDiskCache;
    }
    
/*    public static LoaderConfig getDefault(){
        LoaderConfig config = new LoaderConfig();
        config.mMaxMemeryCacheSize = Runtime.getRuntime().maxMemory() / 8 / 1024;
        config.mMaxDiskCacheSize = 50 * 1024;
        config.mUseDiskCache = true;
        config.mUseMemeryCache = true;
        return config;
    }*/
    
    public static class ConfigBuilder{
        int mMaxMemeryCacheSize; //unit is kb
        int mMaxDiskCacheSize;
        private boolean mUseMemeryCache;
        private boolean mUseDiskCache;
        
        
        public LoaderConfig build(){
            return new LoaderConfig(this);
        }
        
        public static LoaderConfig getDefault(){
            LoaderConfig config = new LoaderConfig();
            config.mMaxMemeryCacheSize = Runtime.getRuntime().maxMemory() / 8 / 1024;
            config.mMaxDiskCacheSize = 50 * 1024;
            config.mUseDiskCache = true;
            config.mUseMemeryCache = true;
            return config;
        }
        
        public ConfigBuilder setMaxMemeryCacheSize(int size){
            
        }
    }
}
