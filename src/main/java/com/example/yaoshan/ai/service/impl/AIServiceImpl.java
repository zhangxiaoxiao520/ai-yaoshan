package com.example.yaoshan.ai.service.impl;

import com.example.yaoshan.ai.dto.AIRequestDTO;
import com.example.yaoshan.ai.dto.AIResponseDTO;
import com.example.yaoshan.ai.service.AIService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * AI养生咨询服务实现类
 */
@Service
public class AIServiceImpl implements AIService {
    
    @Override
    public AIResponseDTO consult(String openid, AIRequestDTO requestDTO) {
        // 由于是模拟环境，返回mock数据
        AIResponseDTO responseDTO = new AIResponseDTO();
        responseDTO.setQuestion(requestDTO.getQuestion());
        responseDTO.setConsultTime(getCurrentTime());
        
        // 根据用户问题生成回复
        String answer = generateAnswer(requestDTO);
        responseDTO.setAnswer(answer);
        
        // 生成推荐的养生方案
        List<AIResponseDTO.HealthSolutionDTO> solutions = generateSolutions(requestDTO);
        responseDTO.setSolutions(solutions);
        
        // 生成推荐的养生食材
        List<AIResponseDTO.FoodDTO> recommendedFoods = generateRecommendedFoods(requestDTO);
        responseDTO.setRecommendedFoods(recommendedFoods);
        
        // 设置注意事项
        responseDTO.setNotices(generateNotices(requestDTO));
        
        // 设置摘要
        responseDTO.setSummary(generateSummary(answer));
        
        return responseDTO;
    }
    
    @Override
    public AIResponseDTO getSuggestionByConstitution(String openid, String constitution) {
        AIResponseDTO responseDTO = new AIResponseDTO();
        responseDTO.setQuestion("根据" + constitution + "体质提供养生建议");
        responseDTO.setConsultTime(getCurrentTime());
        
        String answer = getConstitutionAdvice(constitution);
        responseDTO.setAnswer(answer);
        
        // 生成适合该体质的养生方案
        List<AIResponseDTO.HealthSolutionDTO> solutions = new ArrayList<>();
        AIResponseDTO.HealthSolutionDTO solution = new AIResponseDTO.HealthSolutionDTO();
        solution.setTitle(constitution + "体质调理方案");
        solution.setDescription("针对您的体质特点，制定的个性化调理方案");
        solution.setSteps(Arrays.asList(
                "保持良好的作息习惯，早睡早起",
                "适当进行有氧运动，如散步、太极拳等",
                "保持心情舒畅，避免过度劳累",
                "按照推荐食材合理安排饮食"
        ));
        solution.setRecommendLevel(5);
        solutions.add(solution);
        responseDTO.setSolutions(solutions);
        
        // 生成推荐食材
        List<AIResponseDTO.FoodDTO> recommendedFoods = new ArrayList<>();
        if (constitution.contains("阴虚")) {
            addFood(recommendedFoods, "银耳", "滋阴润肺", "可与百合、莲子同煮", "脾胃虚寒者不宜过量");
            addFood(recommendedFoods, "百合", "清心安神", "可煮粥或煲汤", "风寒咳嗽者不宜");
        } else if (constitution.contains("湿热")) {
            addFood(recommendedFoods, "绿豆", "清热解毒", "可煮绿豆汤", "脾胃虚寒者不宜多吃");
            addFood(recommendedFoods, "冬瓜", "清热利湿", "可煮汤或清炒", "阳虚体质者不宜");
        } else {
            addFood(recommendedFoods, "红枣", "补气养血", "可直接食用或煮粥", "糖尿病患者不宜多吃");
            addFood(recommendedFoods, "山药", "健脾益胃", "可煮粥或炖汤", "无特殊禁忌");
        }
        responseDTO.setRecommendedFoods(recommendedFoods);
        
        // 设置注意事项
        responseDTO.setNotices(Arrays.asList(
                "定期体检，关注健康指标",
                "饮食宜清淡，避免辛辣刺激食物",
                "保持适量运动，增强体质",
                "如有不适，请及时就医"
        ));
        
        responseDTO.setSummary("针对您的" + constitution + "体质，我们提供了个性化的养生建议和调理方案。");
        
        return responseDTO;
    }
    
    @Override
    public AIResponseDTO getSeasonalAdvice(String openid, String season) {
        AIResponseDTO responseDTO = new AIResponseDTO();
        responseDTO.setQuestion(season + "季养生建议");
        responseDTO.setConsultTime(getCurrentTime());
        
        String answer = getSeasonalAdviceContent(season);
        responseDTO.setAnswer(answer);
        
        // 生成季节性养生方案
        List<AIResponseDTO.HealthSolutionDTO> solutions = new ArrayList<>();
        AIResponseDTO.HealthSolutionDTO solution = new AIResponseDTO.HealthSolutionDTO();
        solution.setTitle(season + "季养生方案");
        solution.setDescription("根据" + season + "季特点定制的养生方案");
        
        List<String> steps = new ArrayList<>();
        if ("春".equals(season)) {
            steps.addAll(Arrays.asList(
                    "适量食用温补食物，如春笋、韭菜",
                    "注意保暖，避免春寒侵袭",
                    "多进行户外活动，接触自然",
                    "保持情绪舒畅，养肝护肝"
            ));
        } else if ("夏".equals(season)) {
            steps.addAll(Arrays.asList(
                    "多食用清热解暑食物，如绿豆、西瓜",
                    "避免长时间在烈日下活动",
                    "注意补充水分，防止中暑",
                    "适当午休，保持精力充沛"
            ));
        } else if ("秋".equals(season)) {
            steps.addAll(Arrays.asList(
                    "多食用润肺生津食物，如梨、百合",
                    "注意保暖，防止秋燥伤肺",
                    "适量运动，增强体质",
                    "保持充足睡眠，调节作息"
            ));
        } else if ("冬".equals(season)) {
            steps.addAll(Arrays.asList(
                    "适量食用温补食物，如羊肉、桂圆",
                    "注意保暖，特别是背部和脚部",
                    "适当运动，增强御寒能力",
                    "早睡晚起，顺应自然规律"
            ));
        }
        solution.setSteps(steps);
        solution.setRecommendLevel(4);
        solutions.add(solution);
        responseDTO.setSolutions(solutions);
        
        // 设置注意事项
        responseDTO.setNotices(Arrays.asList(
                "根据季节变化及时调整衣物",
                "注意饮食卫生，避免肠胃不适",
                "保持室内空气流通",
                "如有身体不适，请及时就医"
        ));
        
        responseDTO.setSummary(season + "季养生，顺应自然，调理身心，预防疾病。");
        
        return responseDTO;
    }
    
    @Override
    public AIResponseDTO getFoodAdvice(String openid, String foodType) {
        AIResponseDTO responseDTO = new AIResponseDTO();
        responseDTO.setQuestion(foodType + "的养生功效与食用建议");
        responseDTO.setConsultTime(getCurrentTime());
        
        String answer = getFoodAdviceContent(foodType);
        responseDTO.setAnswer(answer);
        
        // 生成食材信息
        List<AIResponseDTO.FoodDTO> foods = new ArrayList<>();
        
        // 根据食物类型提供具体食材建议
        if ("蔬菜".equals(foodType)) {
            addFood(foods, "菠菜", "补血止血，滋阴润燥", "可清炒或煮汤", "不宜与豆腐同食");
            addFood(foods, "胡萝卜", "补肝明目，健脾和中", "可炒食或榨汁", "不宜过量食用");
            addFood(foods, "西兰花", "防癌抗癌，增强免疫力", "可清炒或凉拌", "无特殊禁忌");
        } else if ("水果".equals(foodType)) {
            addFood(foods, "苹果", "健脾益胃，生津润燥", "可直接食用", "糖尿病患者不宜多吃");
            addFood(foods, "香蕉", "润肠通便，清热解毒", "可直接食用", "脾胃虚寒者不宜");
            addFood(foods, "橙子", "理气化痰，润肺止咳", "可直接食用或榨汁", "胃酸过多者不宜");
        } else {
            // 假设用户输入了具体食物名称
            addFood(foods, foodType, "营养丰富，有益健康", "建议适量食用", "请根据个人体质调整食用量");
        }
        
        responseDTO.setRecommendedFoods(foods);
        
        // 设置注意事项
        responseDTO.setNotices(Arrays.asList(
                "食物多样化，均衡饮食",
                "注意食物的新鲜度和卫生",
                "根据个人体质选择适合的食物",
                "避免暴饮暴食，适量为宜"
        ));
        
        responseDTO.setSummary(foodType + "是养生保健的重要组成部分，合理食用有益健康。");
        
        return responseDTO;
    }
    
    @Override
    public AIResponseDTO getExerciseAdvice(String openid, Integer age, String healthCondition) {
        AIResponseDTO responseDTO = new AIResponseDTO();
        responseDTO.setQuestion(age + "岁，" + healthCondition + "人群的运动养生建议");
        responseDTO.setConsultTime(getCurrentTime());
        
        String answer = getExerciseAdviceContent(age, healthCondition);
        responseDTO.setAnswer(answer);
        
        // 生成运动方案
        List<AIResponseDTO.HealthSolutionDTO> solutions = new ArrayList<>();
        AIResponseDTO.HealthSolutionDTO solution = new AIResponseDTO.HealthSolutionDTO();
        solution.setTitle(age + "岁人群的运动养生方案");
        solution.setDescription("根据您的年龄和健康状况定制的运动方案");
        
        List<String> steps = new ArrayList<>();
        if (age < 40) {
            steps.addAll(Arrays.asList(
                    "每周至少进行3-5次有氧运动，每次30分钟以上",
                    "可选择跑步、游泳、球类运动等",
                    "配合力量训练，增强肌肉力量",
                    "运动前进行充分热身，避免受伤"
            ));
        } else if (age < 60) {
            steps.addAll(Arrays.asList(
                    "每周进行3-4次中等强度运动，每次20-30分钟",
                    "可选择快走、慢跑、太极拳等",
                    "注意保护关节，避免剧烈运动",
                    "保持规律运动，循序渐进"
            ));
        } else {
            steps.addAll(Arrays.asList(
                    "每周进行2-3次轻度运动，每次15-20分钟",
                    "可选择散步、太极、瑜伽等柔和运动",
                    "运动强度以不感到疲劳为宜",
                    "随身携带常用药物，确保安全"
            ));
        }
        solution.setSteps(steps);
        solution.setRecommendLevel(4);
        solutions.add(solution);
        responseDTO.setSolutions(solutions);
        
        // 设置注意事项
        responseDTO.setNotices(Arrays.asList(
                "运动前进行身体检查，确保适合运动",
                "运动强度要适中，避免过度劳累",
                "注意补充水分，保持良好作息",
                "如有不适，立即停止运动并就医"
        ));
        
        responseDTO.setSummary("适合自己年龄和健康状况的运动，才能达到养生保健的效果。");
        
        return responseDTO;
    }
    
    /**
     * 生成AI回答内容
     */
    private String generateAnswer(AIRequestDTO requestDTO) {
        StringBuilder answer = new StringBuilder();
        answer.append("尊敬的用户，感谢您的咨询。");
        
        if (requestDTO.getSymptoms() != null && !requestDTO.getSymptoms().isEmpty()) {
            answer.append("根据您描述的症状：").append(requestDTO.getSymptoms()).append("，");
        }
        
        answer.append("我们为您提供以下养生建议：\n\n");
        
        if (requestDTO.getConstitution() != null && !requestDTO.getConstitution().isEmpty()) {
            answer.append("针对您的").append(requestDTO.getConstitution()).append("体质，建议：\n");
            answer.append("1. 饮食调理：选择适合您体质的食物，保持饮食均衡。\n");
            answer.append("2. 作息规律：保持充足睡眠，顺应自然规律。\n");
            answer.append("3. 适量运动：选择适合自己的运动方式，循序渐进。\n\n");
        }
        
        answer.append("养生保健需要长期坚持，建议您：\n");
        answer.append("1. 保持心情舒畅，避免过度劳累和精神压力。\n");
        answer.append("2. 注意季节变化，及时调整生活习惯。\n");
        answer.append("3. 定期体检，关注身体状况变化。\n");
        answer.append("4. 如有明显不适，请及时就医。");
        
        return answer.toString();
    }
    
    /**
     * 生成养生方案
     */
    private List<AIResponseDTO.HealthSolutionDTO> generateSolutions(AIRequestDTO requestDTO) {
        List<AIResponseDTO.HealthSolutionDTO> solutions = new ArrayList<>();
        
        // 基础养生方案
        AIResponseDTO.HealthSolutionDTO basicSolution = new AIResponseDTO.HealthSolutionDTO();
        basicSolution.setTitle("基础养生方案");
        basicSolution.setDescription("适合大多数人的日常养生方案");
        basicSolution.setSteps(Arrays.asList(
                "饮食规律，三餐定时定量",
                "每天保证7-8小时睡眠",
                "适量运动，每天至少30分钟",
                "保持心情愉悦，避免情绪波动"
        ));
        basicSolution.setRecommendLevel(5);
        solutions.add(basicSolution);
        
        // 根据体质定制方案
        if (requestDTO.getConstitution() != null && !requestDTO.getConstitution().isEmpty()) {
            AIResponseDTO.HealthSolutionDTO constitutionSolution = new AIResponseDTO.HealthSolutionDTO();
            constitutionSolution.setTitle(requestDTO.getConstitution() + "体质调理方案");
            constitutionSolution.setDescription("根据您的体质特点定制的调理方案");
            
            List<String> steps = new ArrayList<>();
            if (requestDTO.getConstitution().contains("阳虚")) {
                steps.addAll(Arrays.asList(
                        "适量食用温补食物，如羊肉、桂圆",
                        "注意保暖，避免受凉",
                        "适当进行户外运动，增加阳气",
                        "避免过度劳累，保持充足睡眠"
                ));
            } else if (requestDTO.getConstitution().contains("阴虚")) {
                steps.addAll(Arrays.asList(
                        "多食用滋阴食物，如银耳、百合",
                        "避免辛辣刺激性食物",
                        "保持心情舒畅，避免情绪激动",
                        "保证充足睡眠，避免熬夜"
                ));
            } else {
                steps.addAll(Arrays.asList(
                        "饮食均衡，不过偏寒热",
                        "保持规律作息，劳逸结合",
                        "适量运动，增强体质",
                        "定期体检，关注健康"
                ));
            }
            constitutionSolution.setSteps(steps);
            constitutionSolution.setRecommendLevel(4);
            solutions.add(constitutionSolution);
        }
        
        return solutions;
    }
    
    /**
     * 生成推荐食材
     */
    private List<AIResponseDTO.FoodDTO> generateRecommendedFoods(AIRequestDTO requestDTO) {
        List<AIResponseDTO.FoodDTO> foods = new ArrayList<>();
        
        // 基础推荐食材
        addFood(foods, "红枣", "补气养血，健脾安神", "可直接食用或煮粥", "糖尿病患者不宜多吃");
        addFood(foods, "山药", "健脾益胃，补肺益肾", "可煮粥、煲汤或清炒", "无特殊禁忌");
        addFood(foods, "枸杞", "滋补肝肾，明目润肺", "可直接食用或泡茶", "感冒发热者不宜");
        
        // 根据体质调整推荐
        if (requestDTO.getConstitution() != null) {
            if (requestDTO.getConstitution().contains("湿热")) {
                addFood(foods, "绿豆", "清热解毒，消暑利水", "可煮绿豆汤", "脾胃虚寒者不宜");
                addFood(foods, "冬瓜", "清热利湿，消肿解毒", "可煮汤或清炒", "阳虚体质者不宜");
            } else if (requestDTO.getConstitution().contains("气虚")) {
                addFood(foods, "黄芪", "补气升阳，固表止汗", "可煮粥或泡水", "感冒发热者不宜");
                addFood(foods, "鸡肉", "温中益气，补精填髓", "可煮汤或清炖", "感冒发热者不宜");
            }
        }
        
        return foods;
    }
    
    /**
     * 生成注意事项
     */
    private List<String> generateNotices(AIRequestDTO requestDTO) {
        List<String> notices = new ArrayList<>();
        notices.add("以上建议仅供参考，不能替代专业医疗诊断和治疗");
        notices.add("如有严重不适，请及时就医，遵医嘱治疗");
        notices.add("养生保健需要长期坚持，循序渐进");
        notices.add("保持良好的生活习惯和乐观的心态");
        
        // 根据用户情况添加特定注意事项
        if (requestDTO.getHealthIssues() != null && !requestDTO.getHealthIssues().isEmpty()) {
            notices.add("针对您提到的健康问题，建议定期进行相关检查");
        }
        
        return notices;
    }
    
    /**
     * 生成摘要
     */
    private String generateSummary(String answer) {
        return "根据您的咨询内容，我们提供了个性化的养生建议和方案。养生保健需要结合个人体质和生活习惯，长期坚持才能达到理想效果。";
    }
    
    /**
     * 获取体质建议内容
     */
    private String getConstitutionAdvice(String constitution) {
        if (constitution.contains("阴虚")) {
            return "阴虚体质的人多表现为手足心热、口燥咽干、失眠多梦等症状。养生要点：1.饮食宜滋阴清热，可多吃银耳、百合、莲子等；2.作息规律，避免熬夜；3.保持心情平静，避免情绪激动；4.适当进行柔和的运动，如瑜伽、太极等。";
        } else if (constitution.contains("阳虚")) {
            return "阳虚体质的人多表现为畏寒肢冷、神疲乏力、大便稀溏等症状。养生要点：1.饮食宜温补，可多吃羊肉、桂圆、生姜等；2.注意保暖，尤其是腰腹和脚部；3.适当进行户外活动，增加阳气；4.保持充足睡眠，避免过度劳累。";
        } else if (constitution.contains("湿热")) {
            return "湿热体质的人多表现为面垢油光、口苦口臭、大便黏滞等症状。养生要点：1.饮食宜清热利湿，可多吃绿豆、冬瓜、薏米等；2.保持环境干燥通风；3.适当进行运动，促进汗液排出；4.避免辛辣油腻食物。";
        } else {
            return "平和体质是最理想的体质类型，但仍需注意养生保健。养生要点：1.保持饮食均衡，不过偏寒热；2.作息规律，劳逸结合；3.适量运动，增强体质；4.保持心情舒畅，预防疾病。";
        }
    }
    
    /**
     * 获取季节性建议内容
     */
    private String getSeasonalAdviceContent(String season) {
        switch (season) {
            case "春":
                return "春季养生应顺应阳气升发的特点。1.饮食宜温补，可多吃春笋、韭菜等；2.注意保暖，避免春寒侵袭；3.多进行户外活动，接触自然；4.保持情绪舒畅，养肝护肝；5.预防春季传染病。";
            case "夏":
                return "夏季养生应注重清热解暑。1.饮食宜清淡，多吃清热生津的食物如绿豆、西瓜等；2.避免长时间在烈日下活动；3.注意补充水分，防止中暑；4.适当午休，保持精力充沛；5.保持室内通风。";
            case "秋":
                return "秋季养生应注重润肺防燥。1.饮食宜滋阴润肺，多吃梨、百合、银耳等；2.注意保暖，防止秋燥伤肺；3.适量运动，增强体质；4.保持充足睡眠，调节作息；5.预防呼吸道疾病。";
            case "冬":
                return "冬季养生应注重温补收藏。1.饮食宜温补，可适当食用羊肉、桂圆等；2.注意保暖，特别是背部和脚部；3.适当运动，增强御寒能力；4.早睡晚起，顺应自然规律；5.预防心脑血管疾病。";
            default:
                return "根据四季变化调整养生方法，顺应自然规律，才能达到健康长寿的目的。";
        }
    }
    
    /**
     * 获取食物建议内容
     */
    private String getFoodAdviceContent(String foodType) {
        if ("蔬菜".equals(foodType)) {
            return "蔬菜是日常饮食中不可或缺的部分，富含维生素、矿物质和膳食纤维。养生要点：1.多吃深色蔬菜，如菠菜、西兰花等，营养价值更高；2.蔬菜宜清淡烹饪，避免高温长时间炒煮；3.每天摄入300-500克蔬菜，种类多样化；4.注意蔬菜的新鲜度和卫生。";
        } else if ("水果".equals(foodType)) {
            return "水果富含维生素、矿物质和抗氧化物质。养生要点：1.每天摄入200-350克水果，不宜过量；2.尽量选择应季水果，营养价值更高；3.最好在两餐之间食用水果；4.糖尿病患者应选择低糖水果，控制摄入量。";
        } else {
            return foodType + "是一种营养丰富的食物，适量食用有益健康。建议在日常饮食中合理搭配，保持饮食多样化。";
        }
    }
    
    /**
     * 获取运动建议内容
     */
    private String getExerciseAdviceContent(Integer age, String healthCondition) {
        StringBuilder advice = new StringBuilder();
        advice.append("根据您的年龄和健康状况，我们为您提供以下运动养生建议：\n\n");
        
        if (age < 40) {
            advice.append("您处于青壮年时期，身体机能较好，适合多种运动方式：\n");
            advice.append("1. 每周进行3-5次中等强度的有氧运动，如跑步、游泳、球类运动等，每次30-60分钟；\n");
            advice.append("2. 配合力量训练，增强肌肉力量和耐力；\n");
            advice.append("3. 注意运动前的热身和运动后的拉伸，预防运动损伤；\n");
            advice.append("4. 根据个人兴趣选择运动项目，保持运动的积极性。");
        } else if (age < 60) {
            advice.append("您处于中年时期，身体机能逐渐下降，运动应注重保养：\n");
            advice.append("1. 每周进行3-4次中等强度运动，如快走、慢跑、太极拳等，每次20-40分钟；\n");
            advice.append("2. 注意保护关节，避免剧烈运动和过度劳累；\n");
            advice.append("3. 适当进行柔韧性训练，保持关节灵活性；\n");
            advice.append("4. 定期进行身体检查，了解健康状况。");
        } else {
            advice.append("您处于老年时期，运动应以安全和舒适为主：\n");
            advice.append("1. 选择轻度的运动方式，如散步、太极、瑜伽等，每次15-30分钟；\n");
            advice.append("2. 运动强度以不感到疲劳为宜，遵循循序渐进的原则；\n");
            advice.append("3. 运动时最好有人陪伴，确保安全；\n");
            advice.append("4. 随身携带常用药物，以备不时之需。");
        }
        
        if (healthCondition.contains("高血压")) {
            advice.append("\n\n高血压患者运动注意事项：\n");
            advice.append("1. 避免剧烈运动和憋气动作；\n");
            advice.append("2. 运动时监测血压变化；\n");
            advice.append("3. 遵医嘱进行适当运动。");
        } else if (healthCondition.contains("糖尿病")) {
            advice.append("\n\n糖尿病患者运动注意事项：\n");
            advice.append("1. 避免空腹运动，预防低血糖；\n");
            advice.append("2. 随身携带糖果，以备低血糖时使用；\n");
            advice.append("3. 选择适合的运动时间，如餐后1-2小时。");
        }
        
        return advice.toString();
    }
    
    /**
     * 添加食物信息
     */
    private void addFood(List<AIResponseDTO.FoodDTO> foods, String name, String effect, String suggestion, String taboos) {
        AIResponseDTO.FoodDTO food = new AIResponseDTO.FoodDTO();
        food.setName(name);
        food.setEffect(effect);
        food.setSuggestion(suggestion);
        food.setTaboos(taboos);
        foods.add(food);
    }
    
    /**
     * 获取当前时间
     */
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}