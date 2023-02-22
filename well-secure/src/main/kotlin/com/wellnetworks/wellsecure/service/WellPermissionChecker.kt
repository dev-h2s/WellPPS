package com.wellnetworks.wellsecure.service

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Target(AnnotationTarget.FUNCTION)
annotation class WellPermissionChecker(val value: Array<String>)

@Aspect
@Component
class WellPermissionCheckerAspect {
    @Pointcut("@annotaion(com.wellnetworks.wellsecure.service.WellPermissionChecker)")
    fun WellPermissionCheckerPointcut(wellPermissionChecker: WellPermissionChecker?) {
    }

    @Around("@annotation(com.wellnetworks.annotations.WellPermissionChecker)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint, wellPermissionChecker: WellPermissionChecker): Any {
        val httpServletRequest: HttpServletRequest = getRequest();

        for (wellPermission in wellPermissionChecker.value) {
            if (httpServletRequest.isUserInRole(wellPermission)) {
                return joinPoint.proceed()
            }
        }

        throw org.springframework.security.access.AccessDeniedException("")
    }

    fun getRequest(): HttpServletRequest {
        val servletRequestAttributes: ServletRequestAttributes =
            (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes)

        return servletRequestAttributes.request
    }
}
