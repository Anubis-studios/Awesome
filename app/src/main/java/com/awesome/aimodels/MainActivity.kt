package com.awesome.aimodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ModelCategory(val displayName: String) {
    ALL("All"),
    LLM("LLM & Text APIs"),
    VIDEO("Video Generation"),
    APP_GAME("App & Game Generators"),
    SKILLS("Agentic Skills & WebApps")
}

data class AIResource(
    val name: String,
    val category: ModelCategory,
    val provider: String,
    val accessType: String, // e.g., "Free API Tier", "Open Weights", "Web Studio / Free Tier"
    val description: String,
    val capabilities: List<String>,
    val url: String,
    val allowEmbedded: Boolean = true
)

val aiResourceCatalog = listOf(
    // App & Game Generators
    AIResource(
        name = "v0 by Vercel",
        category = ModelCategory.APP_GAME,
        provider = "Vercel",
        accessType = "In-App Generator Studio",
        description = "Generative UI system that builds React, Next.js, and Tailwind CSS components and web applications directly inside prompts.",
        capabilities = listOf("React Components", "Tailwind CSS", "Interactive Preview", "In-App Build"),
        url = "https://v0.dev/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Bolt.new",
        category = ModelCategory.APP_GAME,
        provider = "StackBlitz",
        accessType = "In-App Generator Studio",
        description = "In-browser AI app builder that generates full-stack web applications, installs packages, and executes code in-app.",
        capabilities = listOf("Full Stack App Builder", "WebContainer VM", "In-browser Execution", "Deploy Ready"),
        url = "https://bolt.new/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Lovable.dev",
        category = ModelCategory.APP_GAME,
        provider = "Lovable",
        accessType = "In-App Generator Studio",
        description = "Full-stack AI web application generator converting natural language ideas into production React & Supabase apps.",
        capabilities = listOf("Full Stack Web Apps", "React & Vite", "Supabase Backend", "In-App Workspace"),
        url = "https://lovable.dev/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Claude Artifacts Studio",
        category = ModelCategory.APP_GAME,
        provider = "Anthropic",
        accessType = "In-App Generator Studio",
        description = "Interactive execution workspace inside Claude that creates single-page web apps, mini-games, SVG graphics, and tools.",
        capabilities = listOf("Interactive Games", "React & HTML Apps", "Live Sandbox", "Instant Playground"),
        url = "https://claude.ai/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Rosebud AI Game Crafter",
        category = ModelCategory.APP_GAME,
        provider = "Rosebud AI",
        accessType = "In-App Generator Studio",
        description = "Generates 2D & 3D browser games (Three.js / Phaser) from prompts with in-app code editing and asset creation.",
        capabilities = listOf("2D & 3D Web Games", "Phaser.js / Three.js", "Asset Generator", "Playable Games"),
        url = "https://play.rosebud.ai/",
        allowEmbedded = true
    ),

    // Agentic Skills & WebApps (Sourced from sickn33/agentic-awesome-skills)
    AIResource(
        name = "Agentic Skills Discovery Engine",
        category = ModelCategory.SKILLS,
        provider = "Agentic Awesome Skills",
        accessType = "In-App Interactive Suite",
        description = "Explore 2600+ agentic skills, prompt tools, automation stacks, and web app generators directly in-app.",
        capabilities = listOf("2600+ Skills", "AAS Stack", "Workflow Automation", "Direct Generator"),
        url = "https://agentic-awesome-skills.vercel.app/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Open Generative AI Studio",
        category = ModelCategory.SKILLS,
        provider = "Open Generative AI",
        accessType = "In-App Interactive Suite",
        description = "Self-hosted & cloud media studio for generating images, videos, and interactive app prototypes.",
        capabilities = listOf("Generative Media", "Interactive Apps", "Open Source", "Instant Playground"),
        url = "https://github.com/Anil-matcha/Open-Generative-AI",
        allowEmbedded = true
    ),

    // LLMs & Text APIs
    AIResource(
        name = "Google AI Studio (Gemini 1.5 Pro & Flash)",
        category = ModelCategory.LLM,
        provider = "Google",
        accessType = "In-App API Playground",
        description = "High-rate-limit free access to Gemini 1.5 Flash and Gemini 1.5 Pro with huge context window (up to 2M tokens).",
        capabilities = listOf("Text", "Multimodal", "Code", "2M Context Window"),
        url = "https://aistudio.google.com/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Groq LPU Inference Engine",
        category = ModelCategory.LLM,
        provider = "Groq",
        accessType = "In-App API Playground",
        description = "Ultra-fast inference speed for open models like Llama 3.3 70B, DeepSeek R1 Distill, Mixtral, and Gemma 2.",
        capabilities = listOf("Ultra Fast Inference", "Llama 3.3 70B", "DeepSeek R1", "Whisper STT"),
        url = "https://console.groq.com/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Hugging Face Serverless Inference API",
        category = ModelCategory.LLM,
        provider = "Hugging Face",
        accessType = "In-App API Playground",
        description = "Access thousands of open-source LLMs and text generation models hosted on Hugging Face Hub for free.",
        capabilities = listOf("Open Source Models", "Serverless API", "Text Generation", "Embeddings"),
        url = "https://huggingface.co/docs/api-inference/",
        allowEmbedded = true
    ),
    AIResource(
        name = "OpenRouter Free Models",
        category = ModelCategory.LLM,
        provider = "OpenRouter",
        accessType = "In-App API Playground",
        description = "Unified API endpoint offering free access to select open models (e.g. DeepSeek, Llama 3, Qwen 2.5, Mistral).",
        capabilities = listOf("Unified API", "DeepSeek R1", "Llama 3", "Qwen 2.5 Free"),
        url = "https://openrouter.ai/models?max_price=0",
        allowEmbedded = true
    ),
    AIResource(
        name = "Together AI Free Tier",
        category = ModelCategory.LLM,
        provider = "Together AI",
        accessType = "In-App API Playground",
        description = "Fast API endpoint providing free initial credits to access fine-tuned Llama 3, Qwen, and DeepSeek variants.",
        capabilities = listOf("Fast Inference", "Llama 3", "DeepSeek", "Image Models"),
        url = "https://www.together.ai/",
        allowEmbedded = true
    ),
    AIResource(
        name = "Ollama / LM Studio (Local Runner)",
        category = ModelCategory.LLM,
        provider = "Open Source / Local",
        accessType = "Local Open Weights",
        description = "Run open LLMs locally on CPU/GPU with 100% privacy and zero cost.",
        capabilities = listOf("Offline Execution", "Privacy First", "GGUF Quantization", "Custom Modelfiles"),
        url = "https://ollama.com/",
        allowEmbedded = true
    ),

    // Video Generation
    AIResource(
        name = "Wan2.2 & Wan2.1 Video Models",
        category = ModelCategory.VIDEO,
        provider = "Wan-Video / Alibaba Qwen",
        accessType = "Open Weights & In-App Studio",
        description = "State-of-the-art text-to-video, image-to-video, and character animation foundation models.",
        capabilities = listOf("Text-to-Video", "Image-to-Video", "Audio Sync", "480p/720p/1080p"),
        url = "https://github.com/Wan-Video/Wan2.2",
        allowEmbedded = true
    ),
    AIResource(
        name = "LTX-2 & LTX-2.3",
        category = ModelCategory.VIDEO,
        provider = "Lightricks",
        accessType = "Open Weights & In-App Studio",
        description = "Real-time, audio-synchronized video foundation model with LoRA control and keyframe generation.",
        capabilities = listOf("Realtime Video", "Audio Sync", "Keyframe Control", "LoRA Adapters"),
        url = "https://github.com/Lightricks/LTX-Video",
        allowEmbedded = true
    ),
    AIResource(
        name = "HunyuanVideo & HunyuanVideo-1.5",
        category = ModelCategory.VIDEO,
        provider = "Tencent",
        accessType = "Open Weights & In-App Studio",
        description = "High-quality text-to-video and image-to-video model with step-distilled fast inference options.",
        capabilities = listOf("Text-to-Video", "Image-to-Video", "Step Distillation", "High Fidelity"),
        url = "https://github.com/Tencent-Hunyuan/HunyuanVideo",
        allowEmbedded = true
    ),
    AIResource(
        name = "CogVideoX & CogVideoX1.5",
        category = ModelCategory.VIDEO,
        provider = "THUDM / Zhipu AI",
        accessType = "Open Weights & In-App Studio",
        description = "Open-source video diffusion model supporting text-to-video, image-to-video, and video continuation.",
        capabilities = listOf("Text-to-Video", "Image-to-Video", "Video Extension", "ComfyUI Integration"),
        url = "https://github.com/THUDM/CogVideo",
        allowEmbedded = true
    ),
    AIResource(
        name = "Mochi 1",
        category = ModelCategory.VIDEO,
        provider = "Genmo AI",
        accessType = "Open Weights & In-App Studio",
        description = "Open text-to-video diffusion model featuring smooth motion generation and high visual motion clarity.",
        capabilities = listOf("Text-to-Video", "High Motion Quality", "ComfyUI Nodes", "Apache 2.0"),
        url = "https://github.com/genmoai/mochi",
        allowEmbedded = true
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AwesomeAIModelsApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AwesomeAIModelsApp() {
    var selectedCategory by remember { mutableStateOf(ModelCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val filteredResources = remember(selectedCategory, searchQuery) {
        aiResourceCatalog.filter { resource ->
            val matchesCategory = (selectedCategory == ModelCategory.ALL || resource.category == selectedCategory)
            val matchesSearch = searchQuery.isEmpty() ||
                    resource.name.contains(searchQuery, ignoreCase = true) ||
                    resource.provider.contains(searchQuery, ignoreCase = true) ||
                    resource.description.contains(searchQuery, ignoreCase = true) ||
                    resource.capabilities.any { it.contains(searchQuery, ignoreCase = true) }
            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Awesome Free AI Studio",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search generators, LLMs, games, apps...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(ModelCategory.values()) { category ->
                    FilterChip(
                        selected = (selectedCategory == category),
                        onClick = { selectedCategory = category },
                        label = { Text(category.displayName) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "In-App Generators & Catalog (${filteredResources.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Resource List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredResources) { resource ->
                    ResourceCard(
                        resource = resource,
                        onOpenInApp = { url, title ->
                            val intent = Intent(context, GeneratorWebViewActivity::class.java).apply {
                                putExtra("EXTRA_URL", url)
                                putExtra("EXTRA_TITLE", title)
                            }
                            context.startActivity(intent)
                        },
                        onOpenBrowser = { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        onCopyUrl = { url ->
                            clipboardManager.setText(AnnotatedString(url))
                            Toast.makeText(context, "URL copied to clipboard", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ResourceCard(
    resource: AIResource,
    onOpenInApp: (String, String) -> Unit,
    onOpenBrowser: (String) -> Unit,
    onCopyUrl: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = resource.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = resource.accessType,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Provider: ${resource.provider}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = resource.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Capability Tags
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                resource.capabilities.take(3).forEach { cap ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = cap,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onCopyUrl(resource.url) }) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy URL",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { onOpenBrowser(resource.url) },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Launch,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Button(
                        onClick = { onOpenInApp(resource.url, resource.name) },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Use Generator In-App", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
