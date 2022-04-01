#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_hacybeyker_movieoh_di_module_NetworkModule_getApiKeyRelease(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "");
}

JNIEXPORT jstring JNICALL
Java_com_hacybeyker_movieoh_di_module_NetworkModule_getApiKeyQA(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "");
}